/**
 * 
 */
package net.watoud.demo.common.print;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;

/**
 * @author xudong
 *
 */
public class PrintUtils
{
	public static void printCollection(Collection<?> coll, String seperator)
	{
		if (!CollectionUtils.isEmpty(coll))
		{
			Iterator<?> itr = coll.iterator();
			System.out.print("{");
			while (itr.hasNext())
			{
				System.out.print(itr.next());
				if (itr.hasNext())
				{
					System.out.print(seperator);
				}
			}
			System.out.println("}");
		}
	}

	public static void printCollection(Collection<?> coll)
	{
		printCollection(coll, ", ");
	}

	public static <T> void printArray(T[] array)
	{
		if (array != null)
		{
			System.out.println(Arrays.toString(array));
		}
	}

	public static void print(String value)
	{
		System.out.print(value);
	}

	public static void println(String value)
	{
		System.out.println(value);
	}
}
