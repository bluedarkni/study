package com.nijunyang.flink.simple;

import com.alibaba.fastjson2.JSON;
import com.nijunyang.flink.model.Event;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.StringUtils;

import java.time.Instant;
import java.util.Properties;

/**
 * Description:
 * Created by nijunyang on 2022/12/19 14:40
 */
public class SinkToKafka {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000
        DataStream<String> inputDataStream = env.socketTextStream("192.168.0.67", 11111);
        SingleOutputStreamOperator<String> stream = inputDataStream
                .filter(s -> !StringUtils.isNullOrWhitespaceOnly(s) && s.split(",").length == 3)
                .map(s -> {
                    String[] split = s.split(",");
                    return JSON.toJSONString(new Event(split[0], split[1], Instant.ofEpochMilli(Long.parseLong(split[2]))));
                });

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.0.67:9092");
        stream.addSink(new FlinkKafkaProducer<>(
                "test_topic",
                new SimpleStringSchema(),
                properties));
        env.execute();
    }
}
