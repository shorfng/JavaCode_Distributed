package com.loto.b.tagfilter.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class MyProducer1 {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("producer_grp_06");
        producer.setNamesrvAddr("192.168.203.133:9876");
        producer.start();
        Message message = null;

        for (int i = 0; i < 100; i++) {
            message = new Message(
                    "tp_demo_06",
                    "tag-" + (i % 3),
                    ("hello TD - " + i).getBytes()
            );

            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println(e.getMessage());
                }
            });
        }

        Thread.sleep(3_000);
        producer.shutdown();
    }
}
