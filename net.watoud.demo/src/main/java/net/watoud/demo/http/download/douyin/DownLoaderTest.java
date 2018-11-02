package net.watoud.demo.http.download.douyin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

public class DownLoaderTest {
	private final static Pattern PATTERN = Pattern.compile("playAddr[ ]*:[ ]*\"(.*)\"");
	private final static String DIR = "F:\\03.media\\movie\\download";
	private final static String USER_INFOS_URL_PATTERN = "https://www.douyin.com/aweme/v1/aweme/post/?user_id=%s&max_cursor=%d&count=5000";
	private final static HttpClient client = new HttpClient();
	static {
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	}

	public static void main(String[] args) throws Exception {
		downloadByUserId();
	}

	public static void downloadByUserId() throws Exception {
		String url = "https://www.amemv.com/share/user/71945849557?timestamp=1532702253&utm_source=weibo&utm_campaign=client_share&utm_medium=android&app=aweme&iid=38410611361";
			HttpMethod method = new GetMethod(url);
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Failed to get user share query infos: " + method.getStatusLine());
				return;
			}
			String content = method.getResponseBodyAsString();
		System.out.println(content);
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
