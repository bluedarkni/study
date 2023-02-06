package com.nijunyang.flink.simple;

import com.alibaba.fastjson2.JSON;
import com.nijunyang.flink.model.Event;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.flink.util.StringUtils;

import java.time.Instant;

/**
 * Description:
 * Created by nijunyang on 2022/12/19 15:27
 */
public class SinkToRedis {

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
        FlinkJedisPoolConfig redisConfig = new FlinkJedisPoolConfig.Builder()
                .setHost("192.168.0.67")
                .build();
        stream.addSink(new RedisSink<>(redisConfig, new MyRedisMapper()));
        env.execute();
    }

    public static class MyRedisMapper implements RedisMapper<Event> {

        private static final long serialVersionUID = 4561856347479485971L;

        @Override
        public RedisCommandDescription getCommandDescription() {
            return new RedisCommandDescription(RedisCommand.LPUSH, "events");
        }

        @Override
        public String getKeyFromData(Event event) {
            return event.trigger;
        }

        @Override
        public String getValueFromData(Event event) {
            return JSON.toJSONString(event);
        }
    }
}
