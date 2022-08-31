package com;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {

    public final static String QUEUE_NAME="rabbitMQ_test";

    public static void main(String[] args) throws IOException {
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();

            //设置RabbitMQ相关信息
            factory.setHost("127.0.0.1");
            factory.setUsername("admin");
            factory.setPassword("123");
            factory.setPort(54319);

            //创建一个新的连接
            Connection connection = factory.newConnection();

            //创建一个通道
            Channel channel = connection.createChannel();

            //声明要连接的队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("等待消息产生：");

            //创建消费者对象，用于读取消息
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, true, consumer);

            //获取消息列队中的消息,打印在控制台
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("收到消息==============" + message);

            //关闭通道和连接
            channel.close();
            connection.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
