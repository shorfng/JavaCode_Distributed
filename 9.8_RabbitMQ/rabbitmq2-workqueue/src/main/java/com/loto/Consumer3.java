package com.loto;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Consumer3 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:root@192.168.203.133:5672/%2f");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        //保险起见，先声明一下，如果RabbitMQ的虚拟主机中有该队列，当然好，如果没有，则创建
        //此处的队列应该和生产者声明的队列属性等一致
        channel.queueDeclare("queue.wq", true, false, false, null);

        channel.basicConsume("queue.wq", new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("推送来的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println("Cancel: " + consumerTag);
            }
        });
    }
}
