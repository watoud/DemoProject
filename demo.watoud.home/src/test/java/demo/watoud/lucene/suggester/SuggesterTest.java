/**
 * 
 */
package demo.watoud.lucene.suggester;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.search.suggest.fst.WFSTCompletionLookup;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

import demo.watoud.lucene.common.HotWord;
import demo.watoud.lucene.spellchecker.HotWordDictionary;

/**
 * @author xudong
 *
 */
public class SuggesterTest
{

	@Test
	public void suggestTest() throws IOException
	{
		List<HotWord> words = Lists.newArrayList();
		words.add(new HotWord("jianshui", 100));
		words.add(new HotWord("jianquan", 120));
		HotWordDictionary dictionary = new HotWordDictionary(words.iterator());

		SuggesterComputer.Computer computer = SuggesterComputer.computer();
		computer.setLookup(new WFSTCompletionLookup(false));
		computer.setDictionary(dictionary);
		Suggester suggester = computer.compute();

		List<HotWord> results = suggester.suggest("jian", 10);

		Assert.assertThat(2, Is.is(results.size()));

		String[] expected = new String[]
		{ "jianquan", "jianshui" };
		int count = 0;
		Iterator<HotWord> itr = results.iterator();
		while (itr.hasNext())
		{
			HotWord hotword = itr.next();
			Assert.assertThat(expected[count], Is.is(hotword.getWord()));
			++count;
		}

	}
}
