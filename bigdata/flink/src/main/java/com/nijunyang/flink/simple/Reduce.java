package com.nijunyang.flink.simple;

import com.nijunyang.flink.model.Event;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Instant;

/**
 * Description:
 * Created by nijunyang on 2022/12/19 17:22
 */
public class Reduce {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setRuntimeMode(RuntimeExecutionMode.BATCH);

        DataStreamSource<Event> stream = env.fromElements(
                new Event("Jack1", "clickIndex", Instant.now()),
                new Event("Jack1", "clickIndex", Instant.now()),
                new Event("Jack2", "clickIndex", Instant.now()),
                new Event("Jack3", "clickIndex", Instant.now()),
                new Event("Jack4", "clickIndex", Instant.now()),
                new Event("Jack1", "clickPage", Instant.now()));
        stream.map(event -> new Tuple2<>(event.trigger + "_" + event.name, 1))
                .returns(Types.TUPLE(Types.STRING, Types.INT))
                .keyBy(tuple2 -> tuple2.f0)
                .reduce(new ReduceFunction<Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> t1, Tuple2<String, Integer> t2) throws Exception {
                        return Tuple2.of(t1.f0, t1.f1 + t2.f1);
                    }
                })
                .print();
        env.execute();
    }
}
