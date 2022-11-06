package com.nijunyang.rocketmq.consumer;

import lombok.SneakyThrows;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Description:
 * Created by nijunyang on 2020/6/25 12:56
 */
public class Consumer {
    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sync-consumer-group");
        consumer.setNamesrvAddr("192.168.0.67:9876");
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe("sync-topic", "*");
        consumer.subscribe("async-topic", "*");
        consumer.subscribe("oneway-topic", "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @SneakyThrows
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                for (MessageExt messageExt : msgs) {
                    String msg = new String(messageExt.getBody(), "UTF-8");
                    System.out.println(msg);
                }
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
