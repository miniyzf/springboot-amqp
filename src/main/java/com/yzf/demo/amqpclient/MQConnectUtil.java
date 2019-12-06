package com.yzf.demo.amqpclient;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * MQConnectUtil
 *
 * @author Administrator
 * @date 2019/12/4
 */
public class MQConnectUtil {


//    依赖包 与 spring-rabbit 冲突
//    <dependency>
//            <groupId>com.rabbitmq</groupId>
//            <artifactId>amqp-client</artifactId>
//            <version>3.4.1</version>
//        </dependency>

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        factory.setVirtualHost("testhost");
        factory.setUsername("admin");
        factory.setPassword("yangzifu");

        Connection connection = factory.newConnection();
        return connection;

    }
}
