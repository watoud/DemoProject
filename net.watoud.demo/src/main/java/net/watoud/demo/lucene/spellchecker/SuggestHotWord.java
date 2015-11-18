/**
 * 
 */
package net.watoud.demo.lucene.spellchecker;

/**
 * @author xudong
 * 
 *         Copy from org.apache.lucene.search.spell.SuggestWord and add a field
 *         weight.
 *
 */
public final class SuggestHotWord
{
	/**
	 * Creates a new empty suggestion with null text.
	 */
	public SuggestHotWord()
	{
	}

	/**
	 * the weight of the word
	 */
	public long weight;

	/**
	 * the score of the word
	 */
	public float score;

	/**
	 * The freq of the word
	 */
	public int freq;

	/**
	 * the suggested word
	 */
	public String word;
}
