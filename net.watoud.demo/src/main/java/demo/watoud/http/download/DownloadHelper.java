/**
 * 
 */
package demo.watoud.http.download;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @author xudong
 *
 */
public class DownloadHelper
{
	public static String downloadContents(String webUrl) throws IOException
	{
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(webUrl);
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		int statusCode = client.executeMethod(method);

		if (statusCode != HttpStatus.SC_OK)
		{
			System.err.println("Method failed: " + method.getStatusLine());
		}

		byte[] responseBody = method.getResponseBody();

		String contents = new String(responseBody);

		method.releaseConnection();
		
		return contents;
	}
	
	public static Collection<String> matchers(String text,
			String reg)
	{
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(text);

		Set<String> results = new HashSet<String>();

		while (matcher.find())
		{
			String matched = matcher.group();
			results.add(matched);
		}

		return results;

	}
	
	
}
