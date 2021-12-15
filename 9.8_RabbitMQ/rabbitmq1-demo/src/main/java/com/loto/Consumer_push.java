package com.loto;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

public class Consumer_push {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:root@192.168.203.133:5672/%2f");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // 确保MQ中有该队列，如果没有则创建
        channel.queueDeclare("queue.biz", false, false, true, null);

        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {

            }
        };

        // 监听消息，一旦有消息推送过来，就调用第一个lambda表达式
        channel.basicConsume("queue.biz", (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        }, (consumerTag) -> {});

        //channel.close();
        //connection.close();
    }
}
