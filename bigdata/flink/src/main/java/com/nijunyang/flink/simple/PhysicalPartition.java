package com.nijunyang.flink.simple;

import com.nijunyang.flink.model.Event;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Instant;

/**
 * Description: 分区设置
 * Created by nijunyang on 2022/12/19 17:04
 */
public class PhysicalPartition {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        DataStreamSource<Event> stream = env.fromElements(
                new Event("Jack1", "clickIndex", Instant.now()),
                new Event("Jack2", "clickIndex", Instant.now()),
                new Event("Jack3", "clickIndex", Instant.now()),
                new Event("Jack4", "clickIndex", Instant.now()));
        //随机分区
        stream.shuffle().print("shuffle").setParallelism(4);

        //轮询分区
        stream.rebalance().print("rebalance").setParallelism(4);

        //广播
        stream.broadcast().print("broadcast").setParallelism(4);

        //全局分区
        stream.global().print("global").setParallelism(4);

        //自定义分区 按奇偶性
        env.fromElements(1, 2, 3, 4, 5, 6, 7, 8)
                .partitionCustom(new Partitioner<Integer>() {
                    @Override
                    public int partition(Integer key, int numPartitions) {
                        return key % 2;
                    }
                }, new KeySelector<Integer, Integer>() {
                    @Override
                    public Integer getKey(Integer value) throws Exception {
                        return value;
                    }
                })
                .print("partitionCustom").setParallelism(2);
        env.execute();
    }
}
