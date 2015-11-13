/**
 * 
 */
package demo.watoud.lucene.spellchecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import demo.watoud.common.Constants;
import demo.watoud.lucene.common.HotWord;

/**
 * @author xudong
 *
 */
public class HotWordSpellCheckerTest
{

	private HotWordSpellChecker spellChecker;
	private Directory directory;
	private IndexWriterConfig config;

	@Before
	public void init() throws IOException
	{
		String tmpPath = System.getenv("TMP");
		File indexFile = new File(tmpPath, "SpellCheckerIndex");
		this.directory = FSDirectory.open(indexFile);
		this.spellChecker = new HotWordSpellChecker(this.directory);

		Analyzer analyzer = new StandardAnalyzer(
				Constants.CURRENT_LUCENE_VERSION);
		this.config = new IndexWriterConfig(Constants.CURRENT_LUCENE_VERSION,
				analyzer);
	}

	@Test
	public void spellCheckerTest() throws IOException
	{
		List<HotWord> words = new ArrayList<HotWord>();
		words.add(new HotWord("test_n_times", 100));
		words.add(new HotWord("testn_n_times", 150));
		words.add(new HotWord("teat_n_times", 120));

		HotWordDictionary dict = new HotWordDictionary(words.iterator());

		this.spellChecker.clearIndex();
		this.spellChecker.indexDictionary(dict, this.config, false);
		HotWord[] result = this.spellChecker.suggestSimilar("tezt_n_times", 10);

		Assert.assertEquals(3, result.length);
		String[] expected = new String[]
		{ "testn_n_times", "teat_n_times", "test_n_times" };

		Assert.assertEquals(expected[0], result[0].getWord());
		Assert.assertEquals(expected[1], result[1].getWord());
		Assert.assertEquals(expected[2], result[2].getWord());

		Assert.assertTrue(this.spellChecker.exist("test_n_times"));
		Assert.assertEquals(2,
				this.spellChecker.suggestSimilar("test_n_times", 10).length);
	}

	@After
	public void destroy()
	{
		IOUtils.closeQuietly(this.directory);
	}

}
