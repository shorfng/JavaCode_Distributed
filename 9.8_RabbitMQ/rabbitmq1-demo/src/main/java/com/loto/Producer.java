package com.loto;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 建立的连接是否要求自动恢复
        //factory.setAutomaticRecoveryEnabled(false);
        //ExecutorService executor = Executors.factory.setSharedExecutor(executor);

        factory.setHost("192.168.203.133"); // 设置主机名
        factory.setVirtualHost("/");  // 设置虚拟主机名称  /在url中的转义字符 %2f
        factory.setUsername("root");  // 用户名
        factory.setPassword("root");  // 密码
        factory.setPort(5672);       // amqp 的端口号

        // 建立TCP连接
        Connection connection = factory.newConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        // 声明消息队列   消息队列名称
        // 是否是持久化的
        // 是否是排他的
        // 是否是自动删除的
        // 消息队列的属性信息。使用默认值；
        channel.queueDeclare("queue.biz", false, false, true, null);

        // 声明交换器
        // 交换器的名称
        // 交换器的类型
        // 交换器是否是持久化的
        // 交换器是否是自动删除的
        // 交换器的属性map集合
        channel.exchangeDeclare("ex.biz", BuiltinExchangeType.DIRECT, false, false, null);

        // 将交换器和消息队列绑定，并指定路由键
        channel.queueBind("queue.biz", "ex.biz", "hello.world");

        // 发送消息
        // 交换器的名字
        // 该消息的路由键
        // 该消息的属性BasicProperties对象
        // 消息的字节数组
        channel.basicPublish("ex.biz", "hello.world", null, "hello world 2".getBytes());

        // 关闭通道
        channel.close();

        // 关闭连接
        connection.close();
    }
}
