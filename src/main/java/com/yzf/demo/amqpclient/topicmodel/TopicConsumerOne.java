package com.yzf.demo.amqpclient.topicmodel;

import com.rabbitmq.client.GetResponse;
import com.yzf.demo.amqpclient.MQConnectUtil;
import com.rabbitmq.client.*;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * TopicConsumerOne
 *
 * @author Administrator
 * @date 2019/12/5
 */
public class TopicConsumerOne {
    private final static String EXCHANGE_NAME = "test_exchange_topic";
    private final static String QUEUE_NAME = "test_queue_topic_one";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"msg.*");
        channel.basicQos(1);

        /**
         * QueueingConsumer 在 amqp-client 高版本（如 5.4.3 ）中被废弃
         */
        /*QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,false,consumer);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(10);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }*/

        while (true){
            GetResponse response = channel.basicGet(QUEUE_NAME,true);
            if(response != null){
                String message = new String(response.getBody(),"UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        }
    }
}
