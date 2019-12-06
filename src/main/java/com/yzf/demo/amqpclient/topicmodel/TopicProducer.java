package com.yzf.demo.amqpclient.topicmodel;

import com.yzf.demo.amqpclient.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * TopicProducer
 *
 * 通配符（topic）模式
 * 符号 # 匹配零个或多个词；例：msg.# 可以匹配 msg.delete、msg.delete.user、msg.delete.group...
 * 符号 * 匹配一个词；例：msg.* 可以匹配 msg.delete、msg.update 等，不能匹配 msg.delete.user、msg.delete.group...
 * msg.* msg.#  *.msg #.msg *.* #.#
 *
 * @author Administrator
 * @date 2019/12/5
 */
public class TopicProducer {
    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String message = "This is query";
        channel.basicPublish(EXCHANGE_NAME,"msg.query",null,message.getBytes("UTF-8"));
        System.out.println(" [x] send '" + message + "'");

        message = "do something";
        channel.basicPublish(EXCHANGE_NAME,"msg.do",null,message.getBytes("UTF-8"));
        System.out.println(" [x] send '" + message + "'");

        message = "This is delete";
        channel.basicPublish(EXCHANGE_NAME,"msg.delete.user",null,message.getBytes("UTF-8"));
        System.out.println(" [x] send '" + message + "'");

        channel.close();
        connection.close();
    }
}
