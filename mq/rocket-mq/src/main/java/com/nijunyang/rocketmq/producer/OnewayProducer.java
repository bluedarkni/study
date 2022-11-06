package com.nijunyang.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * Description: 单向发送消息，不关心发送结果，比如日志发送
 * Created by nijunyang on 2020/6/25 11:30
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("oneway-producer-group");
        producer.setNamesrvAddr("192.168.0.67:9876");
        producer.start();
        Message msg = new Message("oneway-topic","单向发送消息".getBytes("UTF-8"));
        producer.sendOneway(msg);

        producer.shutdown();
    }
}
