package com.nijunyang.flink;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Description:
 * Created by nijunyang on 2022/11/9 22:09
 */
public class SocketClient {

    public static void main(String[] args) throws Exception {
        //创建流处理执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置并行度，默认值当前计算机的CPU逻辑核数
//        env.setMaxParallelism(1);
//        env.setMaxParallelism(Runtime.getRuntime().availableProcessors());

//        env.setRuntimeMode(RuntimeExecutionMode.BATCH); //执行模式 流、批
        // 从socket中读取数据 nc -lk 11111
        DataStream<String> inputDataStream = env.socketTextStream("106.12.139.147", 11111);
        //基于数据流进行转换计算
        DataStream<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new FlatMapper())
                .keyBy(item -> item.f0)
                .sum("f1");
        resultStream.print();
//        resultStream.writeAsText()
//        resultStream.writeAsCsv()
//        resultStream.writeToSocket()
        //执行任务
        env.execute();
    }

    public static class FlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> out) throws Exception {
            String[] words = s.split(" ");
            for (String str : words) {
                out.collect(new Tuple2<>(str, 1));
            }
        }
    }
}
