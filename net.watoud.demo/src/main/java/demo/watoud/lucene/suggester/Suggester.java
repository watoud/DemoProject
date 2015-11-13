/**
 * 
 */
package demo.watoud.lucene.suggester;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.Lookup.LookupResult;

import com.google.common.collect.Lists;

import demo.watoud.lucene.common.HotWord;

/**
 * @author xudong
 *
 */
public class Suggester
{
	private final Lookup lookup;

	Suggester(Lookup lookup)
	{
		this.lookup = lookup;
	}

	public List<HotWord> suggest(String word, int count)
	{
		List<LookupResult> results = this.lookup.lookup(word, false, count);

		return lookupResultsToHotWords(results);
	}

	private List<HotWord> lookupResultsToHotWords(List<LookupResult> results)
	{
		if (CollectionUtils.isEmpty(results))
		{
			return EMPTY_HOTWORD_ARRAYLIST;
		}

		List<HotWord> hotwords = Lists.newArrayListWithExpectedSize(results
				.size());

		Iterator<LookupResult> itr = results.iterator();
		while (itr.hasNext())
		{
			LookupResult lookupResult = itr.next();
			String word = String.valueOf(lookupResult.key);
			long freq = lookupResult.value;
			hotwords.add(new HotWord(word, freq));
		}

		return hotwords;
	}

	private static final List<HotWord> EMPTY_HOTWORD_ARRAYLIST = Lists
			.newArrayList();
}
