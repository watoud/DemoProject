/**
 * 
 */
package net.xudong.common.json;

/**
 * @author xudong
 *
 */
public class JsonException extends RuntimeException
{

	private static final long serialVersionUID = -5908111094031424396L;

	public JsonException()
	{
		super();
	}

	public JsonException(String s)
	{
		super(s);
	}

	public JsonException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public JsonException(Throwable cause)
	{
		super(cause);
	}

}
