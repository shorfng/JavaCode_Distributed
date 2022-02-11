package com.loto.f.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Producer {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:root@192.168.203.133:5672/%2f");

        try (
                final Connection connection = factory.newConnection();
                final Channel channel = connection.createChannel()) {
                    // 创建队列（实际上使用的是AMQP default这个direct类型的交换器）
                    // 设置队列属性
                    Map<String, Object> arguments = new HashMap<>();

                    // 消息队列中 TTL 消息过期时间，30s
                    arguments.put("x-message-ttl", 30 * 1000);

                    // 设置队列的空闲存活时间（如该队列根本没有消费者，一直没有使用，队列可以存活多久）
                    arguments.put("x-expires", 60 * 1000);  // 60s 后消息过期，消息队列也删除

                    channel.queueDeclare("queue.ttl.waiting",
                            true,
                            false,
                            false,
                            arguments);

                    channel.exchangeDeclare("ex.ttl.waiting",
                            "direct",
                            true,
                            false,
                            null);

                    channel.queueBind("queue.ttl.waiting", "ex.ttl.waiting", "key.ttl.waiting");

                    final AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                            .contentEncoding("utf-8")
                            .deliveryMode(2)   // 持久化的消息
                            .build();

                    channel.basicPublish("ex.ttl.waiting",
                            "key.ttl.waiting",
                            null,
                            "等待的订单号".getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
