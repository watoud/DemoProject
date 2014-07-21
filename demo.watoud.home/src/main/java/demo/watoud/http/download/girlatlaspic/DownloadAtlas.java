/**
 * 
 */
package demo.watoud.http.download.girlatlaspic;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import demo.watoud.http.download.DownloadHelper;
import demo.watoud.http.download.DownloadPic;

/**
 * @author xudong
 *
 */
public class DownloadAtlas
{

	private static final String	MAIN_URL	= "http://girl-atlas.com/t/3170";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		oneSuiteDownload();
	}

	public static void oneSuiteDownload() throws IOException
	{

		File parent = new File("C:\\zfile\\develop\\http\\download");

		File downloadFold = new File(parent, String.valueOf(System
				.currentTimeMillis()));
		downloadFold.mkdir();

		String contents = DownloadHelper.downloadContents("http://girl-atlas.com/a/10130608070500000571");
		Collection<String> jpgUrls = picUrlsFormContents(contents);

		for (String jpgUrl : jpgUrls)
		{
			new DownloadPic().download(jpgUrl, downloadFold);

		}
	}

	public static void colletionDownload() throws IOException
	{
		int suiteCount = 0;
		int currentSuiteNum = 0;
		int suitePicCount = 0;
		int suitePicNum = 0;

		File parent = new File("C:\\zfile\\develop\\http\\download");

		File downloadFold = new File(parent, String.valueOf(System
				.currentTimeMillis()));
		downloadFold.mkdir();

		Collection<String> suites = suiteUrls();
		suiteCount = suites.size();

		for (String suiteUrl : suites)
		{
			currentSuiteNum++;

			String contents = DownloadHelper.downloadContents(suiteUrl);
			Collection<String> jpgUrls = picUrlsFormContents(contents);

			suitePicCount = jpgUrls.size();
			suitePicNum = 0;

			for (String jpgUrl : jpgUrls)
			{
				suitePicNum++;
				new DownloadPic().download(jpgUrl + "!mid", downloadFold);
				new DownloadPic().download(jpgUrl, downloadFold);

				System.out.println("suiteCount:" + suiteCount //
						+ ",currentSuiteNum:" + currentSuiteNum //
						+ ",suitePicCount:" + suitePicCount //
						+ ",suitePicNum:" + suitePicNum);
			}
		}
	}

	private static Collection<String> picUrlsFormContents(String contents)
	{
		Collection<String> targets = DownloadHelper.matchers(contents,
				"delay='http://girlatlas.*jpg");
		Collection<String> targets2 = DownloadHelper.matchers(contents,
				"src='http://girlatlas.b0.upaiyun.com/.*?.jpg");

		targets.addAll(targets2);
		Collection<String> result = new HashSet<String>();

		for (String s : targets)
		{
			String[] splits = StringUtils.split(s, "'", 2);
			String jpgUrl = splits[1];
			result.add(jpgUrl);
			System.out.println(jpgUrl);
		}

		return result;
	}

	private static Collection<String> suiteUrls() throws IOException
	{
		String contents = DownloadHelper.downloadContents(MAIN_URL);

		Collection<String> targets = DownloadHelper.matchers(contents,
				"href='http://girl-atlas.com/a/.*?'");

		Set<String> result = new HashSet<String>();

		for (String target : targets)
		{
			String[] splits = StringUtils.split(target, "'");
			result.add(splits[1]);
		}

		return result;
	}

}
