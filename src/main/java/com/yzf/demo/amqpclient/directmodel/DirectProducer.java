package com.yzf.demo.amqpclient.directmodel;

import com.yzf.demo.amqpclient.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * DirectProducer
 *
 * 路由（direct）模式
 * 路由键 routing key 匹配队列绑定键 binding key ，匹配不到的消息会丢弃
 *
 * @author Administrator
 * @date 2019/12/5
 */
public class DirectProducer {
    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        // 交换机类型 direct
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String message = "Hello World!msg";
        // 消息路由键 routing key:msg/delete
        channel.basicPublish(EXCHANGE_NAME,"msg",null,message.getBytes("UTF-8"));
        System.out.println(" [X] send '" + message + "'");
        message = "Hello World!delete";
        channel.basicPublish(EXCHANGE_NAME,"delete",null,message.getBytes("UTF-8"));
        System.out.println(" [X] send '" + message + "'");
        channel.close();
        connection.close();
    }
}
