package com.loto.controller;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@RestController
public class MessageController {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    //http://localhost:8080/rabbit/abcd
    @RequestMapping("/rabbit/{message}")
    public String receive(@PathVariable String message) throws UnsupportedEncodingException {
        final MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("utf-8")
                .setHeader("hello", "world")
                .build();

        final Message msg = MessageBuilder
                .withBody(message.getBytes(StandardCharsets.UTF_8))
                .andProperties(messageProperties)
                .build();

        rabbitTemplate.send("ex.boot", "key.boot", msg);

        return "ok";
    }
}
