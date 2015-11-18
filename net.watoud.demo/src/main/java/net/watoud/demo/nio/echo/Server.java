/**
 * Server.java
 * 2015Äê11ÔÂ14ÈÕ
 */
package net.watoud.demo.nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.google.common.base.Charsets;

/**
 * @author lixudong
 *
 */
public class Server
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			InetSocketAddress socketAddr = new InetSocketAddress("127.0.0.1", 10006);
			serverChannel.configureBlocking(true);
			serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			serverChannel.bind(socketAddr);

			System.out.println("Echo server starts......");

			while (true)
			{
				final SocketChannel connSocket = serverChannel.accept();
				new Thread(new EchoTask(connSocket), "echo-thread").start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static class EchoTask implements Runnable
	{
		private SocketChannel connSocketChannel;

		public EchoTask(SocketChannel connSocketChannel)
		{
			this.connSocketChannel = connSocketChannel;
		}

		@Override
		public void run()
		{
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			try
			{
				int count = connSocketChannel.read(buffer);
				byte[] content = new byte[count];
				buffer.flip();
				buffer.get(content);
				System.out.println("recieve from client:" + new String(content, Charsets.UTF_8));
				buffer.clear();

				buffer.put(content);
				buffer.flip();
				connSocketChannel.write(buffer);
				connSocketChannel.close();

				System.out.println(connSocketChannel.isOpen());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
