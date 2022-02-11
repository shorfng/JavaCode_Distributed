package com.loto.a.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Consumer_get {
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        // 指定协议： amqp://
        // 指定用户名  root
        // 指定密码   root
        // 指定主机地址   192.168.203.133
        // 指定端口号  5672
        // 指定虚拟主机  %2f
        factory.setUri("amqp://root:root@192.168.203.133:5672/%2f");

        final Connection connection = factory.newConnection();
        System.out.println(connection.getClass());

        final Channel channel = connection.createChannel();

        // 拉消息模式
        // queue：指定从哪个消费者消费消息
        // autoAck：指定是否自动确认消息  true表示自动确认
        final GetResponse getResponse = channel.basicGet("queue.biz", true);

        // 获取消息体  hello world 1
        final byte[] body = getResponse.getBody();
        System.out.println(new String(body));

        //final AMQP.BasicProperties props = getResponse.getProps();

        // 关闭
        channel.close();
        connection.close();
    }
}
