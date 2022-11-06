package com.nijunyang.kafka.binlog;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Description:
 * Created by nijunyang on 2022/7/7 15:23
 */
@Component
public class BinlogConsumer {

    @KafkaListener(id = "listenBinlog", topics = "njy-canal", groupId = "njy-canal-group")
    public void binlog(ConsumerRecord<String, String> record, Acknowledgment ack) throws IOException {
        if (record == null) {
            ack.acknowledge();
            return;
        }
        String key = record.key();
        System.out.println(key);
        System.out.println(record.value());
        ack.acknowledge();
    }

}
