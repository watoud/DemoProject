/**
 * 
 */
package net.watoud.demo.lucene.spellchecker;

import java.util.Comparator;

/**
 * @author xudong
 * 
 *         Copy from org.apache.lucene.search.spell.SuggestWordScoreComparator
 *         and add the compare of weight.
 *
 */
public class SuggestHotWordWeightFirstComparator implements
		Comparator<SuggestHotWord>
{

	public SuggestHotWordWeightFirstComparator()
	{
	}

	public int compare(SuggestHotWord first, SuggestHotWord second)
	{
		if (first.weight > second.weight)
		{
			return 1;
		}
		if (first.weight < second.weight)
		{
			return -1;
		}

		if (first.score > second.score)
		{
			return 1;
		}
		if (first.score < second.score)
		{
			return -1;
		}

		if (first.freq > second.freq)
		{
			return 1;
		}
		if (first.freq < second.freq)
		{
			return -1;
		}
		return second.word.compareTo(first.word);
	}

}
