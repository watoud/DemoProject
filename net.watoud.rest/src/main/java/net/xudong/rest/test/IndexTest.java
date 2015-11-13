/**
 * 
 */
package net.xudong.rest.test;

import java.io.File;
import java.io.IOException;

import net.xudong.common.constants.Constants;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

/**
 * @author xudong
 *
 */
@Service
public class IndexTest
{
	public static void main(String[] args) throws IOException
	{
		Analyzer analyzer = new StandardAnalyzer(
				Constants.CURRENT_LUCENE_VERSION);
		IndexWriterConfig iwc = new IndexWriterConfig(
				Constants.CURRENT_LUCENE_VERSION, analyzer);
		File indexDir = new File("C:\\zdevelop\\lucene\\index");
		Directory directory = FSDirectory.open(indexDir);
		IndexWriter indexWriter = new IndexWriter(directory, iwc);
	}

}
