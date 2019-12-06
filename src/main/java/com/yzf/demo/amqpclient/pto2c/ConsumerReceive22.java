package com.yzf.demo.amqpclient.pto2c;

import com.yzf.demo.amqpclient.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ConsumerReceive
 *
 * 自动确认、手动确认
 *
 * @author Administrator
 * @date 2019/12/4
 */
public class ConsumerReceive22 {
    public final static String QUEUE_NAME = "test_queue_2";

    /**
     * 自动确认
     * 1、消费者1和消费者2获取到的消息内容是不同的，同一个消息只能被一个消费者获取。
     * 2、消费者1和消费者2获取到的消息的数量是相同的，一个是消费奇数号消息，一个是偶数。
     * 3、只要消息从队列中获取，无论消费者获取到消息后是否成功消息，都认为是消息已经成功消费。
     *
     * 手动确认
     * 1、能者多劳
     * 2、消费者1（sleep10）比消费者2（sleep500）获取的消息更多
     * 3、消费者从队列中获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，如果消费者一直没有反馈，那么该消息将一直处于不可用状态。
     * @param args
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MQConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 同一时刻服务器只会发一条消息给消费者(配合手动确认模式)
        //channel.basicQos(1);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME, true, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(500);
        }

        // 手动确认模式
        /*QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME_1, false, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(500);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }*/
    }
}
