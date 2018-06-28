package net.watoud.demo.http.download.douyin;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

import net.watoud.demo.http.download.douyin.model.UserShareVediosQueryResponse;
import net.watoud.demo.http.download.douyin.model.UserShareVediosQueryResponse.Aweme;

public class DownLoaderByUserID {
	private final static Pattern PATTERN = Pattern.compile("playAddr[ ]*:[ ]*\"(.*)\"");
	private final static String DIR = "F:\\03.media\\movie\\download";
	private final static String USER_INFOS_URL_PATTERN = "https://www.douyin.com/aweme/v1/aweme/post/?user_id=%s&max_cursor=%d&count=5000";
	private final static HttpClient client = new HttpClient();
	static {
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.err.println("usage： userId cursor");
			return;
		}
		downloadByUserId(args[0], Integer.valueOf(args[1]));
	}

	public static void downloadByUserId(String userId, int cursor) throws Exception {
		// 獲取所有分享的視頻
		boolean hasMore = true;
		long curCursor = cursor;
		while (hasMore) {
			String url = String.format(USER_INFOS_URL_PATTERN, userId, curCursor);
			HttpMethod method = new GetMethod(url);
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Failed to get user share query infos: " + method.getStatusLine());
				return;
			}
			String content = method.getResponseBodyAsString();
			UserShareVediosQueryResponse response = JSONObject.parseObject(content, UserShareVediosQueryResponse.class);
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
			for (Aweme ele : response.getAwemeList()) {
				System.out.println("waitint 5s ...");
				TimeUnit.SECONDS.sleep(5);
				System.out.println("end ...");
				System.out.println("total size: " + totalSize + ", current size: " + cur + ", has more: " + hasMore);
				String shareUrl = ele.getShareInfo().getShareUrl();
				HttpMethod vedioShareInfoGet = new GetMethod(shareUrl);
				statusCode = client.executeMethod(vedioShareInfoGet);
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Failed to share vedio info, status: " + method.getStatusLine() + ", share url:"
							+ shareUrl);
					continue;
				}
				String vedioInfo = vedioShareInfoGet.getResponseBodyAsString();
				if (StringUtils.isEmpty(vedioInfo)) {
					System.err.println("Vedio info is empty, share url:" + shareUrl);
					continue;
				}
				String vedioUrl = parseVedioUrlFromHtml(vedioInfo);
				if (StringUtils.isEmpty(vedioUrl)) {
					System.err.println("Failed to get vedio url, share url:" + shareUrl);
					continue;
				}
				System.out.println("vedioUrl:" + vedioUrl);
				vedioUrl = vedioUrl.replace("playwm", "play");
				HttpMethod vedioGet = new GetMethod(vedioUrl);
				setHeaders(vedioGet);
				statusCode = client.executeMethod(vedioGet);
				if (vedioGet.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					System.err.println(
							"Failed to get vedio, status: " + vedioGet.getStatusLine() + ", vedio url:" + vedioUrl);
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
	
	public static void setHeaders(HttpMethod mothed) {
		mothed.setRequestHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		mothed.setRequestHeader("Accept-Encoding", "gzip, deflate");
		mothed.setRequestHeader("If-Range", "512B588648435943E6A482A33492C6B5");
		mothed.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		mothed.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		mothed.setRequestHeader("Host", "v3-dy-z.ixigua.com");
	}

	public static String parseVedioUrlFromHtml(String content) {
		Matcher mathcer = PATTERN.matcher(content);
		if (mathcer.find()) {
			return mathcer.group(1);
		}
		return StringUtils.EMPTY;
	}
}
