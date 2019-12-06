package com.yzf.demo.amqpclient.fanoutmodel;

import com.yzf.demo.amqpclient.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * FanoutProducer
 *  订阅（fanout）模式
 * 1、1个生产者，多个消费者
 * 2、每一个消费者都有自己的一个队列
 * 3、生产者没有将消息直接发送到队列，而是发送到了交换机
 * 4、每个队列都要绑定到交换机
 * 5、生产者发送的消息，经过交换机，到达队列，实现，一个消息被多个消费者获取的目的
 * 注意：消息发送到没有队列绑定的交换机时，消息将丢失，因为，交换机没有存储消息的能力，消息只能存在在队列中。
 * @author Administrator
 * @date 2019/12/5
 */
public class FanoutProducer {
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明交换机 exchange 交换机类型 fanout
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String message = "Hello World!";
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes("UTF-8"));
        System.out.println(" [x] Send '" + message + "'");

        channel.close();
        connection.close();
    }
}
