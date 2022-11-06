package com.nijunyang.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by nijunyang on 2019/12/7 22:20
 */
@Component
public class MyConsumer {

    @KafkaListener(topics = {"mytopic"}, groupId = "test")
    public void listen(ConsumerRecord<String, String> record) {
//        String value = record.value();
//        System.out.println(value);
//        System.out.println(record);
    }
}
