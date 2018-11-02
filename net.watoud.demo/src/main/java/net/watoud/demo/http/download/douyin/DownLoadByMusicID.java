package net.watoud.demo.http.download.douyin;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
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

import net.watoud.demo.http.download.douyin.model.MusicShareVediosQueryResponse;
import net.watoud.demo.http.download.douyin.model.UserShareVediosQueryResponse.Aweme;

public class DownLoadByMusicID {
	private final static int RETRY_COUNT = 5;
	private final static Pattern PATTERN = Pattern.compile("playAddr[ ]*:[ ]*\"(.*)\"");
	private final static String DIR = "F:\\03.media\\movie\\download";
	private final static String MUSIC_INFOS_URL_PATTERN = "https://www.iesdouyin.com/aweme/v1/music/aweme/?music_id=%s&count=18&cursor=%d&aid=1128";

	private final static String VEDIO_DOWN_LOAD_URL_PATTERN = "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=%s";
	private final static HttpClient client = new HttpClient();
	static {
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		client.getParams().setConnectionManagerTimeout(TimeUnit.HOURS.toMillis(10));
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.err.println("usage： musicId cursor");
			return;
		}
		download(args[0], Integer.valueOf(args[1]));
	}

	public static void download(String musicId, long cursor) throws Exception {

		// 獲取所有分享的視頻
		boolean hasMore = true;
		long curCursor = cursor, preCursor = cursor;
		int retry = 0;
		while (hasMore && retry <RETRY_COUNT) {
			try {
				String url = String.format(MUSIC_INFOS_URL_PATTERN, musicId, curCursor);
				HttpMethod method = new GetMethod(url);
				int statusCode = client.executeMethod(method);
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Failed to get music relative query infos: " + method.getStatusLine());
					return;
				}
				String content = method.getResponseBodyAsString();
				MusicShareVediosQueryResponse response = JSONObject.parseObject(content,
						MusicShareVediosQueryResponse.class);
				if (response == null || CollectionUtils.isEmpty(response.getAwemeList())) {
					System.err.println("failed to parse music relative query response," + String.valueOf(content));
					return;
				}
				if (response.getHasMore() == 0) {
					hasMore = false;
				}
				preCursor = curCursor;
				curCursor = response.getCursor();
				int totalSize = response.getAwemeList().size();
				int currentCur = 0;
				// 处理每一个视频
				for (Aweme ele : response.getAwemeList()) {
					System.out.println("++++++++++++++++++++++++++++");
					// if (!StringUtils.endsWith(ele.getStatistics().getDiggCount(), "w") &&
					// Integer.valueOf(ele.getStatistics().getDiggCount())<6000) {
					// System.out.println("已经到了非热门视频， 小心心小于6000，不再下载"); // 结果是按照热度降序排序的
					// return;
					// }
					currentCur++;
					System.out.println("waiting 3s ...");
					TimeUnit.SECONDS.sleep(3);
					System.out.println("end ...");
					System.out.println("total size: " + totalSize + ", current size: " + currentCur + ", has more: " + hasMore);
					String vedioUri = ele.getVedioInfo().getPlayAddr().getUri();
					String vedioUrl = String.format(VEDIO_DOWN_LOAD_URL_PATTERN, vedioUri);
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
					File fileDir = new File(DIR, musicId);
					if (!fileDir.exists()) {
						fileDir.mkdir();
					}
					String uid = ele.getAuthor().getUid() == null ? "" : ele.getAuthor().getUid();
					FileOutputStream out = new FileOutputStream(
							new File(fileDir, musicId + "_" + ele.getId() + "_" + uid + ".mp4"));
					out.write(vedioGet.getResponseBody());
					out.close();
					System.out.println(
							"success: " + musicId + "_" + ele.getId() + ", 热度：" + ele.getStatistics());
					retry = 0;
				}
			} catch (ConnectException e) {
				curCursor = preCursor;
				System.out.println(e.getMessage());
				retry++;
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
