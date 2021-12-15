package com.loto;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConsumerListenerApp {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(com.loto.RabbitConfig.class);
    }
}
