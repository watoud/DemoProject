/**
 * 
 */
package net.watoud.demo.lucene.spellchecker;

import java.util.Comparator;

import org.apache.lucene.util.PriorityQueue;

/**
 * @author xudong
 *
 */
public class SuggestHotWordQueue extends PriorityQueue<SuggestHotWord>
{
	public static final Comparator<SuggestHotWord> DEFAULT_COMPARATOR = new SuggestHotWordWeightFirstComparator();

	private Comparator<SuggestHotWord> comparator;

	public SuggestHotWordQueue(int size)
	{
		super(size);
		comparator = DEFAULT_COMPARATOR;
	}

	public SuggestHotWordQueue(int size, Comparator<SuggestHotWord> comparator)
	{
		super(size);
		this.comparator = comparator;
	}

	@Override
	protected boolean lessThan(SuggestHotWord a, SuggestHotWord b)
	{
		int val = comparator.compare(a, b);
		return val < 0;
	}
}
