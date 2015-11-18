/**
 * Client.java
 * 2015Äê11ÔÂ14ÈÕ
 */
package net.watoud.demo.nio.echo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Charsets;

/**
 * @author lixudong
 *
 */
public class Client
{
	public static void main(String[] args)
	{
		try
		{
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);
			InetSocketAddress socketAddr = new InetSocketAddress("127.0.0.1", 10006);
			socketChannel.connect(socketAddr);
			ByteBuffer buffer = ByteBuffer.allocate(30);
			buffer.put("this is a test".getBytes(Charsets.UTF_8));
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();

			int count = socketChannel.read(buffer);
			System.out.println("recieve bytes:" + count);
			byte[] content = new byte[count];
			buffer.flip();

			buffer.get(content);

			System.out.println(new String(content, Charsets.UTF_8));

			buffer.clear();
			int code = socketChannel.read(buffer);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(code);

			buffer.clear();
			buffer.put(content);
			buffer.flip();
			System.out.println(socketChannel.isConnected());
			code = socketChannel.write(buffer);
			System.out.println(code);
			socketChannel.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
