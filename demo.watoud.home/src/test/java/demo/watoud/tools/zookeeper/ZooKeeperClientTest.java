/**
 * 
 */
package demo.watoud.tools.zookeeper;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xudong
 *
 */
public class ZooKeeperClientTest
{

	private ZookeeperClient zkClient;

	@Before
	public void init() throws IOException
	{
		zkClient = new ZookeeperClient("127.0.0.1:2181", 6000, new Watcher()
		{

			@Override
			public void process(WatchedEvent event)
			{
				// do nothing
			}
		});
	}

	@Test
	public void createNodeTest() throws ZooKeeperException
	{
		String path = "/testRoot";
		this.zkClient.create(path, null, Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		Assert.assertTrue(this.zkClient.isExists(path));

		if (this.zkClient.isExists(path))
		{
			this.zkClient.delete(path);
		}
	}

	@Test
	public void createNodeRecursively() throws ZooKeeperException
	{
		String path = "/testRoot/watoud/test";
		this.zkClient.createReCursive(path,
				"jianshui".getBytes(Charset.defaultCharset()),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		Assert.assertTrue(this.zkClient.isExists(path));
		// Assert.assertTrue(message, condition);

		if (this.zkClient.isExists(path))
		{
			this.zkClient.deleteRecursive(path);
		}
	}

	@After
	public void destroy()
	{
		IOUtils.closeQuietly(zkClient);
	}
}
