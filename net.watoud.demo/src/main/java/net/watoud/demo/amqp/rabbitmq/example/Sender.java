/**
 * 
 */
package net.watoud.demo.amqp.rabbitmq.example;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author xudong
 *
 */
public class Sender
{

	private final static String QUEUE_NAME = "hello";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		// factory.setUsername("watoud");
		// factory.setPassword("watoud");

		Connection connection = null;
		Channel channel = null;
		try
		{

			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World!";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("[¡Ì] Sent '" + message + "'");

		}
		finally
		{
			if (channel != null)
			{
				channel.close();
			}

			if (connection != null)
			{
				connection.close();
			}
		}

	}
}
