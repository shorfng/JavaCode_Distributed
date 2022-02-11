package com.loto.c.consumer.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;

/**
 *【消息可靠性】保证消息被消费者成功消费（Consumer ACK）
 */
public class MyConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:root@192.168.203.133:5672/%2f");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare("queue.ca", false, false, false, null);

        // 拉消息的模式
        //final GetResponse getResponse = channel.basicGet("queue.ca", false);
        //channel.basicReject(getResponse.getEnvelope().getDeliveryTag(), true);

        // 推消息模式
        // autoAck:false 表示手动确认消息
        channel.basicConsume("queue.ca", false, "myConsumer", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));

                // 确认消息，手动ack
                //channel.basicAck(envelope.getDeliveryTag(), false);

                // 可以用于拒收多条消息
                // 参数1 deliveryTag：表示消息的唯一标志，消息的标签
                // 参数2 multiple：表示不确认多个消息还是一个消息，是否是批量确认
                // 参数3 requeue：表示不确认的消息是否需要重新入列，然后重发
                //channel.basicNack(envelope.getDeliveryTag(), false, true);

                // 用于拒收一条消息
                // 对于不确认的消息，是否重新入列，然后重发
                channel.basicReject(envelope.getDeliveryTag(), false);
            }
        });

        //channel.close();
        //connection.close();
    }
}
