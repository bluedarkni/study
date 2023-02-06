package com.nijunyang.flink.simple;

import com.nijunyang.flink.model.Event;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.StringUtils;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Description:
 * Created by nijunyang on 2022/12/19 14:57
 */
public class SinkToMysql {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000
        DataStream<String> inputDataStream = env.socketTextStream("192.168.0.67", 11111);
        SingleOutputStreamOperator<Event> stream = inputDataStream
                .filter(s -> !StringUtils.isNullOrWhitespaceOnly(s) && s.split(",").length == 3)
                .map(s -> {
                    String[] split = s.split(",");
                    return new Event(split[0], split[1], Instant.ofEpochMilli(Long.parseLong(split[2])));
                });
        stream.addSink(
                JdbcSink.sink(
                        "INSERT INTO events (trigger, name, timestamp) VALUES (?, ?, ?)",
                        (statement, event) -> {
                            statement.setString(1, event.trigger);
                            statement.setString(2, event.name);
                            statement.setTimestamp(3, Timestamp.from(event.timestamp));
                        },
                        new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                                .withUrl("jdbc:mysql://192.168.0.67:3306/njy")
                                .withDriverName("com.mysql.jdbc.Driver")
                                .withUsername("root")
                                .withPassword("root")
                                .build()
                )
        );

        env.execute();
    }


}
