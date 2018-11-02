package net.watoud.demo.http.download.douyin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
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
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;

import net.watoud.demo.http.download.douyin.model.UserShareVediosQueryResponse;
import net.watoud.demo.http.download.douyin.model.UserShareVediosQueryResponse.Aweme;

public class DownLoaderByUserInfo {


	private final static String DIR = "F:\\03.media\\movie\\download";
	private final static String VEDIO_DOWN_LOAD_URL_PATTERN = "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=%s";
	private final static HttpClient client = new HttpClient();
	static {
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		client.getParams().setConnectionManagerTimeout(TimeUnit.HOURS.toMillis(10));
	}

	public static void main(String[] args) throws Exception {
		downloadByUserId(args[0]);
	}

	public static void downloadByUserId(String userId) throws Exception {
		List<String> handledFiles = new ArrayList<>();
		File infoDir = new File("F:\\03.media\\movie\\download\\config");
		File[] jsonFiles = infoDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// if (name.endsWith(".json")) {
				// return true;
				// }
				return true;
			}
		});
		if (jsonFiles != null && jsonFiles.length >= 1) {
			int fileNum = 0;
			for (File file : jsonFiles) {
				fileNum++;
				String response = FileUtils.readFileToString(file, Charsets.UTF_8);
				UserShareVediosQueryResponse vedioResponse=null;
				try {
					  vedioResponse = JSONObject.parseObject(response,
							UserShareVediosQueryResponse.class);
				} catch (Exception e) {
					System.out.println("error process file:" + file.getName());
					continue;
				}
				
				if (response == null || CollectionUtils.isEmpty(vedioResponse.getAwemeList())) {
					System.err.println("failed to parse response," + String.valueOf(response));
					return;
				}
				int currentCur = 0, totalSize = vedioResponse.getAwemeList().size();
				for (Aweme ele : vedioResponse.getAwemeList()) {
					currentCur++;
					System.out.println("waiting 3s ...");
					TimeUnit.SECONDS.sleep(3);
					System.out.println("end ...");
					System.out.println(
							"total size: " + totalSize + ", current size: " + currentCur + ", fileNum: " + fileNum);
					if (ele == null || ele.getVedioInfo() == null || ele.getVedioInfo().getPlayAddr() == null) {
						System.out.println("can not find vedio play addr");
						continue;
					}
					String vedioUri = ele.getVedioInfo().getPlayAddr().getUri();
					String vedioUrl = String.format(VEDIO_DOWN_LOAD_URL_PATTERN, vedioUri);
					System.out.println("vedioUrl:" + vedioUrl);
					vedioUrl = vedioUrl.replace("playwm", "play");
					HttpMethod vedioGet = new GetMethod(vedioUrl);
					setHeaders(vedioGet);
					int statusCode = client.executeMethod(vedioGet);
					if (statusCode != HttpStatus.SC_OK) {
						System.err.println(
								"Failed to get vedio, status: " + vedioGet.getStatusLine() + ", vedio url:" + vedioUrl);
						continue;
					}
					File fileDir = new File(DIR, userId);
					if (!fileDir.exists()) {
						fileDir.mkdir();
					}
					FileOutputStream out = new FileOutputStream(
							new File(fileDir, userId + "_" + ele.getId() + ".mp4"));
					out.write(vedioGet.getResponseBody());
					out.close();
					System.out.println("success: " + userId + "_" + ele.getId() + ", »»∂»£∫" + ele.getStatistics()
							+ ", handled files:" + handledFiles);
				}
				handledFiles.add(file.getName());
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

}
