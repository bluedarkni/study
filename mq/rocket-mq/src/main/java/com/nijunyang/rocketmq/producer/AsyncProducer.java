package com.nijunyang.rocketmq.producer;

import lombok.SneakyThrows;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * Description: 异步发送消息
 * Created by nijunyang on 2020/6/25 11:12
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer mqProducer = new DefaultMQProducer("async-producer-group");
        mqProducer.setNamesrvAddr("192.168.0.67:9876");
        mqProducer.start();
        mqProducer.setRetryTimesWhenSendAsyncFailed(0);
        final CountDownLatch2 countDownLatch = new CountDownLatch2(10);
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("async-topic", ("你好rocketMQ异步消息" + i).getBytes("utf-8"));
            mqProducer.send(msg, new SendCallback() {
                @SneakyThrows
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    Thread.sleep(1000);
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println(1);
        countDownLatch.await();
//        countDownLatch.await(5, TimeUnit.SECONDS);
        mqProducer.shutdown();
    }
}
