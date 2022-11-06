package com.nijunyang.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Description:  同步发送消息，可靠性要求较高的场景
 * Created by nijunyang on 2020/6/25 10:52
 */
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        // 实例化消息生产者Producer
        DefaultMQProducer mqProducer = new DefaultMQProducer("sync-producer-group");
        // 设置NameServer的地址
        mqProducer.setNamesrvAddr("192.168.0.67:9876");
        mqProducer.start();
        Message msg = new Message("sync-topic", ("你好rocketMQ" + Math.random()).getBytes("utf-8"));
        //发送消息
        SendResult sendResult = mqProducer.send(msg);
        System.out.println(sendResult);

        // 如果不再发送消息，关闭Producer实例。
        mqProducer.shutdown();

    }
}
