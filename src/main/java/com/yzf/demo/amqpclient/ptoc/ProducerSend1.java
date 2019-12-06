package com.yzf.demo.amqpclient.ptoc;

import com.yzf.demo.amqpclient.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ProducerSend
 *
 * 一个生产者，一个消费者
 *
 * @author Administrator
 * @date 2019/12/4
 */

public class ProducerSend1 {
    private final static String QUEUE_NAME = "test_queue_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false, false,null);

        String message = "发送消息";
        channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("UTF-8"));
        System.out.println(" [x] Send '" + message + "'");

        channel.close();
        connection.close();
    }
}
