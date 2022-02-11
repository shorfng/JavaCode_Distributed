package com.loto.b.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Producer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:root@192.168.203.133:5672/%2f");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // 声明fanout类型的交换器
        channel.exchangeDeclare("ex.myfanout", "fanout", true, false, null);

        for (int i = 0; i < 20; i++) {
            channel.basicPublish("ex.myfanout",
                    "",  // fanout 类型的交换器不需要指定路由键
                    null,
                    ("hello world fan:" + i).getBytes(StandardCharsets.UTF_8));
        }

        channel.close();
        connection.close();
    }
}
