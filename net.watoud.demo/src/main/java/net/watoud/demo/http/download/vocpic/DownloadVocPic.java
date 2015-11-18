/**
 * 
 */
package net.watoud.demo.http.download.vocpic;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.watoud.demo.http.download.DownloadHelper;
import net.watoud.demo.http.download.DownloadPic;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xudong
 *
 */
public class DownloadVocPic
{

	private static final String PIC_MAIN_PAGE = "http://bbs.voc.com.cn/mm/index.php?type=1";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		String url = "http://bbs.voc.com.cn/mm/meinv-6051268-19-1.html";
		File parent = new File("C:\\zfile\\develop\\http\\download");

		File downloadFold = new File(parent, String.valueOf(System
				.currentTimeMillis()));
		downloadFold.mkdir();

		String contents = DownloadHelper.downloadContents(url);

		Collection<String> jpgUrls = picUrlFormContents(contents);
		DownloadPic downloader = new DownloadPic();

		int count = 0;
		for (String jpgUrl : jpgUrls)
		{
			downloader.download(jpgUrl, downloadFold);
			++count;
			System.out.println("The " + count + "th pic...");
		}
	}

	public static void downLoadAll() throws IOException, HttpException
	{
		int suiteCount = 0;
		int currentSuiteNum = 0;
		int currentSuitePicCount = 0;
		int currentSuitePicNum = 0;

		File parent = new File("C:\\zfile\\develop\\http\\download");

		File downloadFold = new File(parent, String.valueOf(System
				.currentTimeMillis()));
		downloadFold.mkdir();

		Collection<String> picHttpUrls = picsHttpUrls();
		suiteCount = picHttpUrls.size();

		DownloadPic downloader = new DownloadPic();

		System.out.println(picHttpUrls.size());

		for (String s : picHttpUrls)
		{
			currentSuiteNum++;
			String contents = DownloadHelper.downloadContents(s);

			Collection<String> jpgUrls = picUrlFormContents(contents);
			currentSuitePicCount = jpgUrls.size();
			currentSuitePicNum = 0;

			for (String jpgUrl : jpgUrls)
			{
				currentSuitePicNum++;
				downloader.download(jpgUrl, downloadFold);

				System.out.println("suiteCount:" + suiteCount //
						+ ",currentSuiteNum:" + currentSuiteNum //
						+ ",currentSuitePicCount:" + currentSuitePicCount //
						+ ",currentSuitePicNum:" + currentSuitePicNum);
			}

		}
	}

	public static Collection<String> picsHttpUrls() throws IOException,
			HttpException
	{

		String contents = DownloadHelper.downloadContents(PIC_MAIN_PAGE);

		Collection<String> targets = DownloadHelper.matchers(contents,
				"a href=\"http://bbs.voc.com.cn/mm/.*?html");
		// ∑¿÷π÷ÿ∏¥¡¥Ω”
		Set<String> results = new HashSet<String>();

		for (String s : targets)
		{
			String[] splits = StringUtils.split(s, "\"", 2);
			String picsUrl = splits[1];
			results.add(picsUrl);
		}

		for (String s : results)
		{
			System.out.println(s);
		}

		return results;
	}

	public static Collection<String> picUrlFormContents(String contents)
	{
		Collection<String> targets = DownloadHelper.matchers(contents,
				"imgList.*jpg");
		Collection<String> result = new HashSet<String>();

		for (String s : targets)
		{
			String[] splits = StringUtils.split(s, "\"", 2);
			String jpgUrl = splits[1];
			result.add(jpgUrl);
			System.out.println(jpgUrl);
		}

		return result;
	}

}
