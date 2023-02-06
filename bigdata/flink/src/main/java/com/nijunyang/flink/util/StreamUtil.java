package com.nijunyang.flink.util;

import com.nijunyang.flink.model.Event;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.StringUtils;

import java.time.Instant;

/**
 * Description:
 * Created by nijunyang on 2023/1/7 11:29
 */
public class StreamUtil {

    public static SingleOutputStreamOperator<Event> getAndPreProcessStream(StreamExecutionEnvironment env) {
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000
        return env.socketTextStream("106.12.139.147", 11111)
                .filter(value -> {
                    //System.out.println(value);
                    return !StringUtils.isNullOrWhitespaceOnly(value) && value.split(",").length == 3;
                })
                .map(value -> {
                    String[] split = value.split(",");
                    return new Event(split[0], split[1], Instant.ofEpochMilli(Long.parseLong(split[2])));
                });
    }
}
