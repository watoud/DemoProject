package net.watoud.demo.http.download.douyin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

import net.watoud.demo.http.download.douyin.model.ChallengeIdVediosQueryResponse;

public class DownloadByChallenge {
	private final static String DIR = "F:\\03.media\\movie\\download";
	private final static String CHANNEL_INFOS_URL_PATTERN = "https://www.iesdouyin.com/aweme/v1/challenge/aweme/?ch_id=%s&count=9&screen_limit=3&download_click_limit=3&cursor=%d&aid=1128";
	private final static String VEDIO_DOWN_LOAD_URL_PATTERN = "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=%s";
	private final static HttpClient client = new HttpClient();
	static {
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.err.println("usage： userId cursor");
			return;
		}
		String[] challengeIDs = StringUtils.split(args[0], ",");
		List<String> handled = new ArrayList<>();
		if (challengeIDs != null && challengeIDs.length != 0) {
			for (String challenge : challengeIDs) {
				downloadBychallengeId(challenge, Integer.valueOf(args[1]));
				handled.add(challenge);
				System.out.println("handled challenges:" + handled);
			}
		}
	}

	public static void downloadBychallengeId(String userId, int cursor) throws Exception {
		// 獲取所有分享的視頻
		boolean hasMore = true;
		long curCursor = cursor;
		while (hasMore) {
			String url = String.format(CHANNEL_INFOS_URL_PATTERN, userId, curCursor);
			String signature = generateSignature(userId, curCursor);
			if (StringUtils.isEmpty(signature)) {
				System.out.println("Failed to get signature");
				continue;
			}
			url = url + "&_signature=" + signature;
			HttpMethod method = new GetMethod(url);
			setHeaders(method);
			System.out.println("url:" + url);
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Failed to get user share query infos: " + method.getStatusLine());
				return;
			}
			String content = method.getResponseBodyAsString();
			ChallengeIdVediosQueryResponse response = JSONObject.parseObject(content,
					ChallengeIdVediosQueryResponse.class);
			if (response == null || CollectionUtils.isEmpty(response.getAwemeList())) {
				System.err.println("failed to parse response," + String.valueOf(content));
				return;
			}
			if (response.getHasMore() == 0) {
				hasMore = false;
			}
			curCursor = response.getMaxCursor();
			int totalSize = response.getAwemeList().size();
			int cur = 0;
			// 处理每一个视频
			for (net.watoud.demo.http.download.douyin.model.ChallengeIdVediosQueryResponse.Aweme ele : response
					.getAwemeList()) {
				System.out.println("waitint 1s ...");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("end ...");
				System.out.println("total size: " + totalSize + ", current size: " + cur + ", has more: " + hasMore);

				String vedioUri = ele.getVedioInfo().getPlayAddr().getUri();
				String vedioUrl = String.format(VEDIO_DOWN_LOAD_URL_PATTERN, vedioUri);
				System.out.println("vedioUrl:" + vedioUrl);
				vedioUrl = vedioUrl.replace("playwm", "play");
				HttpMethod vedioGet = new GetMethod(vedioUrl);
				setHeaders(vedioGet);
				boolean flag = true;
				int count = 0;
				while (flag && count < 3) {
					flag = false;
					try {
						statusCode = client.executeMethod(vedioGet);
						if (statusCode != HttpStatus.SC_OK) {
							System.err.println("Failed to get vedio, status: " + vedioGet.getStatusLine()
									+ ", vedio url:" + vedioUrl);
							continue;
						}
					} catch (Exception e) {
						e.printStackTrace();
						flag = true;
						count++;
					}
				}
				if (count == 3) {
					continue;
				}
				File fileDir = new File(DIR, userId);
				if (!fileDir.exists()) {
					fileDir.mkdir();
				}
				FileOutputStream out = new FileOutputStream(new File(fileDir, userId + "_" + ele.getId() + ".mp4"));
				out.write(vedioGet.getResponseBody());
				out.close();
				System.out.println("success: " + userId + "_" + ele.getId());
				cur++;
			}
		}
	}

	public static String generateSignature(String channelId, long cursor) throws Exception {

		Process process = Runtime.getRuntime().exec(
				"D:\\ProgramFiles\\nodejs\\node.exe  D:\\04.Sources\\amemv-crawler\\fuck-byted-acrawler.js "
						+ (channelId + "9" + cursor)
		);
		int code = process.waitFor();
		if (code != 0) {
			System.out.println("Failed to generate signature, returned code is" + code);
			return null;
		}
		try (InputStream in = process.getInputStream();) {
			List<String> ret = IOUtils.readLines(in);
			if (CollectionUtils.isEmpty(ret)) {
				return null;
			}
			return ret.get(0);
		}
	}

	public static void setHeaders(HttpMethod mothed) {
		mothed.setRequestHeader("accept",
				"application/json,text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		// mothed.setRequestHeader("accept-encoding", "gzip, deflate, br");
		mothed.setRequestHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
		mothed.setRequestHeader("upgrade-insecure-requests", "1");
		mothed.setRequestHeader("User-Agent",
				"Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
	}
}
