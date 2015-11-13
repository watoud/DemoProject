/**
 * 
 */
package demo.watoud.http.download.girlatlaspic;

import java.io.IOException;
import java.util.Collection;

import demo.watoud.http.download.DownloadHelper;

/**
 * @author xudong
 *
 */
public class Test
{

	private static final String	MAIN_URL	= "http://www.umei.cc/p/gaoqing/rihan/20120203215149.htm";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		String contents = DownloadHelper.downloadContents(MAIN_URL);

		Collection<String> targets = DownloadHelper.matchers (contents,
				"http://i3.umei.cc/img.*?jpg");

//		Set<String> result = new HashSet<String>();

		for (String target : targets)
		{
			System.out.println(target);
		}
		
		System.out.println("++++++++");

//		 FileOutputStream out = new FileOutputStream(
//		 "C:\\zfile\\develop\\http\\main.html");
//		
//		 out.write(contents.getBytes());
//		 out.close();
//		System.out.println("OK!!!");
	}

}
