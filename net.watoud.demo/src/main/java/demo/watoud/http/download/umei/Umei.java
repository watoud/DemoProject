/**
 * 
 */
package demo.watoud.http.download.umei;


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
public class Umei
{

	private static final String	MAIN_URL	= "http://www.umei.cc/p/gaoqing/rihan/index-1.htm";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
//		colletionDownload();
		
		oneSuiteDownload();
	}

	public static void oneSuiteDownload()throws IOException
	{
		String webUrl = "http://www.umei.cc/p/gaoqing/rihan/20120926213114.htm";
		
		File parent = new File("C:\\zfile\\develop\\http\\download");

		File downloadFold = new File(parent, String.valueOf(System
				.currentTimeMillis()));
		downloadFold.mkdir();
		
		System.out.println("Begin..."+webUrl);
		int pageNumber = getSuitePageCounter(webUrl);
		
		String prefix = StringUtils.replace(webUrl, ".htm", StringUtils.EMPTY);
		System.out.println("Prefix = " + prefix);
		

		String contents = DownloadHelper.downloadContents(webUrl);
		Collection<String> jpgUrls = picUrlsFormContents(contents);

		System.out.println("size: " + jpgUrls.size());
		
		for (String jpgUrl : jpgUrls)
		{
			new DownloadPic().download(jpgUrl, downloadFold);
		}
		
		for (int i = 2 ; i<=pageNumber;i++ )
		{
			String pageUrl = prefix+"_"+String.valueOf(i)+".htm";
			
			String InnerContents = DownloadHelper.downloadContents(pageUrl);
			Collection<String> innerJpgUrls = picUrlsFormContents(InnerContents);
			
			for (String jpgUrl : innerJpgUrls)
			{
				new DownloadPic().download(jpgUrl, downloadFold);

				System.out.println("Inner download...");
			}
			
		}
		
		System.out.println("End..." +webUrl);
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
		System.out.println("Suite size = "+suites.size());

		for (String suiteUrl : suites)
		{
			System.out.println("------------------------------------------------");
			System.out.println("Begin..."+suiteUrl);
			int pageNumber = getSuitePageCounter(suiteUrl);
			System.out.println("Page Counter:"+pageNumber);
			
			String prefix = StringUtils.replace(suiteUrl, ".htm", StringUtils.EMPTY);
			System.out.println("Prefix = " + prefix);
			
			currentSuiteNum++;

			String contents = DownloadHelper.downloadContents(suiteUrl);
			Collection<String> jpgUrls = picUrlsFormContents(contents);

			suitePicCount = jpgUrls.size();
			suitePicNum = 0;

			for (String jpgUrl : jpgUrls)
			{
				suitePicNum++;
				new DownloadPic().download(jpgUrl, downloadFold);

				System.out.println("suiteCount:" + suiteCount //
						+ ",currentSuiteNum:" + currentSuiteNum //
						+ ",suitePicCount:" + suitePicCount //
						+ ",suitePicNum:" + suitePicNum);
			}
			
			
			for (int i = 2 ; i<=pageNumber;i++ )
			{
				String pageUrl = prefix+"_"+String.valueOf(i)+".htm";
				
				String InnerContents = DownloadHelper.downloadContents(pageUrl);
				Collection<String> innerJpgUrls = picUrlsFormContents(InnerContents);
				
				for (String jpgUrl : innerJpgUrls)
				{
					new DownloadPic().download(jpgUrl, downloadFold);

					System.out.println("Inner download...");
				}
				
			}
			
			System.out.println("End..." +suiteUrl);
		}
	}
	public static int getSuitePageCounter(String url) throws IOException
	{
		String contents = DownloadHelper.downloadContents(url);
		
		Collection<String> targets = DownloadHelper.matchers(contents,
				"'[0-9]*?_[0-9]*?.htm'");
		
		int value = -1;
		
		for (String s:targets)
		{
			String[] splits = StringUtils.split(s, "_.");
			int tmp = Integer.valueOf(splits[1]);
			
			if (value < tmp)
			{
				value=tmp;
			}
		}
		
		return value;
	}
	
	private static Collection<String> picUrlsFormContents(String contents)
	{
		Collection<String> targets = DownloadHelper.matchers(contents,
				"http://i9.umei.cc/img.*?jpg");
		
		return targets;
	}

	private static Collection<String> suiteUrls() throws IOException
	{
		String contents = DownloadHelper.downloadContents(MAIN_URL);

		Collection<String> targets = DownloadHelper.matchers(contents,
				"/p/gaoqing/rihan/[0-9]*.htm");

		Set<String> result = new HashSet<String>();

		for (String target : targets)
		{
			String real = "http://www.umei.cc/"+target;
			result.add(real);
		}

		return result;
	}

}
