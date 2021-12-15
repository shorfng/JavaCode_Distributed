package com.loto.b.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Consumer3 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:root@192.168.203.133:5672/%2f");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        final String queueName = channel.queueDeclare().getQueue();
        System.out.println("生成的临时队列的名字为：" + queueName);

        channel.exchangeDeclare("ex.myfanout",
                BuiltinExchangeType.FANOUT,
                true,
                false,
                null);

        // fanout类型的交换器绑定不需要 routingkey
        channel.queueBind(queueName, "ex.myfanout", "");

        channel.basicConsume(queueName, (consumerTag, message) -> {
            System.out.println("Three  " + new String(message.getBody(), StandardCharsets.UTF_8));
        }, consumerTag -> {
        });
    }
}
