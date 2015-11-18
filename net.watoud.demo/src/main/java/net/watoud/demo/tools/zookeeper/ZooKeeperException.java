/**
 * 
 */
package net.watoud.demo.tools.zookeeper;

/**
 * @author xudong
 *
 */
public class ZooKeeperException extends Exception
{
	private static final long serialVersionUID = -484354560180859739L;

	public ZooKeeperException()
	{
		super();
	}

	public ZooKeeperException(String s)
	{
		super(s);
	}

	public ZooKeeperException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ZooKeeperException(Throwable cause)
	{
		super(cause);
	}
}
