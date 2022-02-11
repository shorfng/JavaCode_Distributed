package com.loto;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Rocketmq2SpringbootProducerApplication.class})
public class Rocketmq2SpringbootProducerApplicationTests {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSendMessage() {
        // 用于向broker发送消息
        // 第一个参数是topic名称
        // 第二个参数是消息内容
        this.rocketMQTemplate.convertAndSend(
                "tp_springboot_01",
                "springboot: hello TD"
        );
    }

    @Test
    public void testSendMessages() {
        for (int i = 0; i < 10; i++) {
            // 用于向broker发送消息
            // 第一个参数是topic名称
            // 第二个参数是消息内容
            this.rocketMQTemplate.convertAndSend(
                    "tp_springboot_01",
                    "springboot: hello TD " + i
            );
        }
    }
}
