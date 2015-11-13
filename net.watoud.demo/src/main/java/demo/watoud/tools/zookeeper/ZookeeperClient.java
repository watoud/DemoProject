/**
 * 
 */
package demo.watoud.tools.zookeeper;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

/**
 * @author xudong
 *
 */
public class ZookeeperClient implements Closeable
{

	private ZooKeeper zookeeper;

	public ZookeeperClient(String host, int timeout, Watcher watcher)
			throws IOException
	{
		zookeeper = new ZooKeeper(host, timeout, watcher);
	}

	public String create(String path, byte[] data, List<ACL> acl,
			CreateMode createMode) throws ZooKeeperException
	{
		try
		{
			return this.zookeeper.create(path, data, acl, createMode);
		}
		catch (KeeperException e)
		{
			throw new ZooKeeperException(
					"The server returns a non-zero error code or the ACL is invalid, null, or empty");
		}
		catch (InterruptedException e)
		{
			throw new ZooKeeperException("The transaction is interrupted");
		}
	}

	public void createReCursive(String path, byte[] data, List<ACL> acl,
			CreateMode createMode) throws ZooKeeperException
	{
		if (this.isExists(path))
		{
			throw new ZooKeeperException("The node[" + path + "] exists.");
		}

		String[] pathParts = StringUtils.split(path, "/");
		if (pathParts == null)
		{
			throw new IllegalArgumentException("Path cannot be null");
		}

		int length = pathParts.length;

		byte[] content = null;
		for (int i = 0; i < length; i++)
		{
			if (i == length - 1)
			{
				content = data;
			}
			String joinPath = nodePathJoin(pathParts, i, "/");
			if (!isExists(joinPath))
			{
				this.create(joinPath, content, acl, createMode);
			}
		}
	}

	public void deleteRecursive(String path) throws ZooKeeperException
	{
		String[] pathParts = StringUtils.split(path, "/");
		if (pathParts == null)
		{
			throw new IllegalArgumentException("Path cannot be null");
		}

		int length = pathParts.length;
		for (int i = length - 1; i >= 0; i--)
		{
			String nodePath = nodePathJoin(pathParts, i, "/");
			if (isExists(nodePath))
			{
				delete(nodePath);
			}
		}
	}

	public void delete(String path) throws ZooKeeperException
	{
		if (!isExists(path))
		{
			throw new ZooKeeperException("Failed to delete node[" + path
					+ "],because the path is not exist.");
		}

		try
		{
			this.zookeeper.delete(path, -1);
		}
		catch (KeeperException e)
		{
			throw new ZooKeeperException(
					"the server signals an error with a non-zero error code.",
					e);
		}
		catch (InterruptedException e)
		{
			throw new ZooKeeperException(
					"The server transaction is interrupted.", e);
		}
	}

	public boolean isExists(String path) throws ZooKeeperException
	{
		try
		{
			Stat stat = this.zookeeper.exists(path, false);
			return stat != null;
		}
		catch (KeeperException e)
		{
			throw new ZooKeeperException("The zkServer signals an error.", e);
		}
		catch (InterruptedException e)
		{
			throw new ZooKeeperException(
					"The server transaction is interrupted.", e);
		}
	}

	private String nodePathJoin(String[] array, int count, String seperator)
	{
		if (count < 0 || count >= array.length)
		{
			throw new IllegalArgumentException("Parameter count[" + count
					+ "]is invalid.");
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= count; i++)
		{
			builder.append(seperator).append(array[i]);
		}

		return builder.toString();
	}

	@Override
	public void close()
	{
		if (this.zookeeper != null)
		{
			try
			{
				this.zookeeper.close();
			}
			catch (InterruptedException e)
			{
				// ignore
			}
		}
	}
}
