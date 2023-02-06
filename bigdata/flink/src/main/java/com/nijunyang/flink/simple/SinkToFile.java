package com.nijunyang.flink.simple;

import com.alibaba.fastjson2.JSON;
import com.nijunyang.flink.model.Event;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.apache.flink.util.StringUtils;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created by nijunyang on 2022/12/19 11:44
 */
public class SinkToFile {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000
        DataStream<String> inputDataStream = env.socketTextStream("106.12.139.147", 11111);
        SingleOutputStreamOperator<Event> stream = inputDataStream
                .filter(s -> !StringUtils.isNullOrWhitespaceOnly(s) && s.split(",").length == 3)
                .map(s -> {
                    String[] split = s.split(",");
                    return new Event(split[0], split[1], Instant.ofEpochMilli(Long.parseLong(split[2])));
                });
        StreamingFileSink<String> fileSink = StreamingFileSink
                .<String>forRowFormat(new Path("./output"), new SimpleStringEncoder<>("UTF-8"))
                .withRollingPolicy(DefaultRollingPolicy.builder()
                        .withRolloverInterval(TimeUnit.MINUTES.toMillis(15))
                        .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
                        .withMaxPartSize(1024 * 1024 * 1024)
                        .build())
                .build();
        stream.map(JSON::toJSONString).addSink(fileSink);
        env.execute();
    }
}
