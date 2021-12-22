package com.loto.f.delay.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class MyProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("producer_grp_10_01");
        producer.setNamesrvAddr("192.168.203.133:9876");
        producer.start();

        Message message = null;

        for (int i = 0; i < 20; i++) {
            message = new Message("tp_demo_10", ("hello TD - " + i).getBytes());

            // 设置延迟时间级别 0-18
            // 0 表示不延迟
            // 18表示延迟2h
            // 大于18的都是2h
            message.setDelayTimeLevel(i);
            producer.send(message);
        }

        producer.shutdown();
    }
}
