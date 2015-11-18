/**
 * 
 */
package net.watoud.demo.lucene.suggester;

import java.io.IOException;

import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.suggest.Lookup;

/**
 * @author xudong
 *
 */
public class SuggesterComputer
{

	public static Computer computer()
	{
		return new Computer();
	}

	public static class Computer
	{
		private Lookup lookup;
		private Dictionary dictionary;

		private Computer()
		{
		}

		public Computer setLookup(Lookup lookup)
		{
			this.lookup = lookup;
			return this;
		}

		public Computer setDictionary(Dictionary dictionary)
		{
			this.dictionary = dictionary;
			return this;
		}

		public Suggester compute() throws IOException
		{
			if (this.lookup == null || this.dictionary == null)
			{
				throw new IllegalArgumentException("");
			}
			this.lookup.build(this.dictionary);
			return new Suggester(this.lookup);
		}
	}
}
