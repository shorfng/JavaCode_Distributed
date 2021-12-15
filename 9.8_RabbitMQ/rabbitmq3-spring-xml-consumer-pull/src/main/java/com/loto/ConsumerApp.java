package com.loto;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ConsumerApp {
    public static void main(String[] args) throws UnsupportedEncodingException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-rabbit.xml");
        final RabbitTemplate template = context.getBean(RabbitTemplate.class);
        final Message message = template.receive("queue.q1");

        // 拉消息模式
        System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));

        context.close();
    }
}
