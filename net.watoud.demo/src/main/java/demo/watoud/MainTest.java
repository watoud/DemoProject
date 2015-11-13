/**
 * 
 */
package demo.watoud;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author xudong
 *
 */
public class MainTest
{
	private Directory directory;
	public MainTest(File dir, IndexWriterConfig config) throws IOException
	{
		directory = FSDirectory.open(dir);
		DirectoryReader.open(this.directory);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
	}
}
