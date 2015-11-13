/**
 * 
 */
package net.xudong.search.engine;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

/**
 * @author xudong
 *
 */
@Service
public class SearchEngine implements Closeable
{
	private IndexWriter writer;
	private Directory directory;
	private IndexReader indexReader;

	public SearchEngine(File dir, IndexWriterConfig config) throws IOException
	{
		directory = FSDirectory.open(dir);
		this.writer = new IndexWriter(directory, config);
	}

	public IndexWriter indexer()
	{
		return this.writer;
	}

	public IndexSearcher searcher() throws IOException
	{
		this.indexReader = DirectoryReader.open(this.directory);
		IndexSearcher indexSearcher = new IndexSearcher(this.indexReader);

		return indexSearcher;
	}

	public void close() throws IOException
	{
		IOUtils.closeQuietly(this.writer);
		IOUtils.closeQuietly(this.indexReader);
		IOUtils.closeQuietly(this.directory);
	}
}
