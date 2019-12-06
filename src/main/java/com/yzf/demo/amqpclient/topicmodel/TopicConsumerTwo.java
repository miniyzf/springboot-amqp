package com.yzf.demo.amqpclient.topicmodel;

import com.rabbitmq.client.*;
import com.yzf.demo.amqpclient.MQConnectUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * TopicConsumerTwo
 *
 * @author Administrator
 * @date 2019/12/5
 */
public class TopicConsumerTwo {
    private final static String EXCHANGE_NAME = "test_exchange_topic";
    private final static String QUEUE_NAME = "test_queue_topic_two";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"msg.#");
        channel.basicQos(1);

        /*QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,false,consumer);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(10);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }*/

        channel.basicConsume(QUEUE_NAME,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
