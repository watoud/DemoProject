/**
 * 
 */
package demo.watoud.lucene.spellchecker;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.TermFreqIterator;
import org.apache.lucene.util.BytesRef;

import demo.watoud.lucene.common.HotWord;

/**
 * @author xudong
 *
 */
public class HotWordDictionary implements Dictionary
{
	private Iterator<HotWord> words;

	public HotWordDictionary(Iterator<HotWord> words)
	{
		this.words = words;
	}

	public TermFreqIterator getWordsIterator() throws IOException
	{
		return new HotWordIterator();
	}

	final class HotWordIterator implements TermFreqIterator
	{
		private long weight;
		private BytesRef word = new BytesRef();

		public long weight()
		{
			return weight;
		}

		public BytesRef next()
		{
			while (words.hasNext())
			{
				HotWord hotword = words.next();
				weight = hotword.getWeight();
				word.copyChars(hotword.getWord());

				return word;
			}

			return null;
		}

		public Comparator<BytesRef> getComparator()
		{
			return null;
		}
	}

}
