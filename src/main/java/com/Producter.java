package com;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Producter {
    public final static String QUEUE_NAME="rabbitMQ_test";

    public static void main(String[] args) {
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();

            //设置RabbitMQ相关信息
            factory.setHost("127.0.0.1");
            factory.setUsername("admin");
            factory.setPassword("123");
            factory.setPort(8081);

            //创建一个新的连接
            Connection connection = factory.newConnection();
            //创建一个通道
            Channel channel = connection.createChannel();
            // 声明一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //发送消息到队列中
            String message = "Hello RabbitMQ";
            channel.basicPublish("",QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("Producer Send +'" + message + "'");

            //关闭通道和连接
            channel.close();
            connection.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
