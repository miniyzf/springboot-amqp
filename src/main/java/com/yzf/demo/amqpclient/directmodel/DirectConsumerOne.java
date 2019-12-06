package com.yzf.demo.amqpclient.directmodel;

import com.yzf.demo.amqpclient.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * DirectConsumerOne
 *
 * @author Administrator
 * @date 2019/12/5
 */
public class DirectConsumerOne {
    private final static String EXCHANGE_NAME = "test_exchange_direct";
    private final static String QUEUE_NAME = "test_queue_direct1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 队列绑定键 binding key:msg  匹配路由键 routing key:msg 的消息
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"msg");
        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,false,consumer);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody(),"utf-8");
            System.out.println(" [x] Received '" + message +"'");
            Thread.sleep(10);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }

    }
}
