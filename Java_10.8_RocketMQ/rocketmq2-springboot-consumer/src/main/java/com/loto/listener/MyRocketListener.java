package com.loto.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "tp_springboot_01", consumerGroup = "consumer_grp_03")
public class MyRocketListener implements RocketMQListener<String> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyRocketListener.class);

    @Override
    public void onMessage(String message) {
        // 处理broker推送过来的消息
        log.info(message);
    }
}
