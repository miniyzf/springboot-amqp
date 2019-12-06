package com.yzf.demo.amqpclient.pto2c;

import com.yzf.demo.amqpclient.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ProducerSend
 *
 * 一个生产者，二个消费者
 * 一条消息只能被一个消费者获取
 *
 * @author Administrator
 * @date 2019/12/4
 */
public class ProducerSend2 {
    private final static String QUEUE_NAME = "test_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false, false,null);

        for (int i = 0; i < 100; i++) {
            String message = "发送消息 " + i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
            System.out.println(" [x] Send '" + message + "'");
            Thread.sleep(i*10);
        }

        channel.close();
        connection.close();
    }
}
