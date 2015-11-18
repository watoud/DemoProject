/**
 * 
 */
package net.watoud.demo.lucene.common;

/**
 * @author xudong
 *
 */
public class HotWord
{
	private String word;
	private long weight;

	public HotWord(String word, long weight)
	{
		this.word = word;
		this.weight = weight;
	}

	public String getWord()
	{
		return this.word;
	}

	public long getWeight()
	{
		return this.weight;
	}
}
