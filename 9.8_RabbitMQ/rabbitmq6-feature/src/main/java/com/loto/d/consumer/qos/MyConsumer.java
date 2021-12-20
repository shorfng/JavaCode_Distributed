package com.loto.d.consumer.qos;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:root@192.168.203.133:5672/%2f");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare("queue.qos", false, false, false, null);

        // 使用basic做限流，仅对消息推送模式生效。

        // 方式1
        // 表示Qos是10个消息，最多有10个消息等待确认
        channel.basicQos(10);

        // 方式2
        // 表示最多10个消息等待确认
        // global=true，表示只要是使用当前的channel的Consumer，该设置都生效
        // global=false，表示仅限于当前Consumer
        //channel.basicQos(10, false);

        // 方式3
        // 第一个参数表示未确认消息的大小，Rabbit没有实现，不用管。
        //channel.basicQos(1000, 10, true);

        channel.basicConsume("queue.qos", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // some code going on
                // 可以批量确认消息，减少每个消息都发送确认带来的网络流量负载。
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        });

        channel.close();
        connection.close();
    }
}
