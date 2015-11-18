/**
 * 
 */
package net.watoud.demo.amqp.rabbitmq.example;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * @author xudong
 *
 */
public class Reciever
{
	private final static String QUEUE_NAME = "hello";

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ConsumerCancelledException
	 * @throws ShutdownSignalException
	 */
	public static void main(String[] args) throws IOException,
			ShutdownSignalException, ConsumerCancelledException,
			InterruptedException
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);

		while (true)
		{
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}

	}

}
