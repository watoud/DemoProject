/**
 * 
 */
package net.watoud.demo.http.download;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xudong
 *
 */
public class DownloadPic
{

	private final HttpClient	client;
	
	private static Object  lock = new Object();

	private static int			allCount	= 0;

	public DownloadPic ()
	{
		client = new HttpClient();
	}

	public void download(String picUrl, File fold)
	{
		if (picUrl == null || fold == null)
		{
			System.err.println("picUrl or fold is null.");
			return;
		}

		if (!fold.isDirectory())
		{
			System.err.println("The fold [" + fold.getName()
					+ "] is not a directory.");
			return;
		}

		HttpMethod method = new GetMethod(picUrl);
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		try
		{
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK)
			{
				System.err.println("Method failed: " + method.getStatusLine());
				return;
			}

			byte[] responseBody = method.getResponseBody();
			
			int index = StringUtils.lastIndexOf(picUrl, "/");
			String picName = StringUtils.substring(picUrl, index + 1);
			
			/////
			if (picName.contains("!mid"))
			{
				picName=StringUtils.replacePattern(picName, "!mid", StringUtils.EMPTY);
			}

			FileOutputStream out = new FileOutputStream(new File(fold, picName));
			out.write(responseBody);
			out.close();

			synchronized(lock)
			{
				allCount++;
			}

			System.out.println("The downloaded count:" + allCount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}

	}

}
