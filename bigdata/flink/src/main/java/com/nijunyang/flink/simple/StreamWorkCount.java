package com.nijunyang.flink.simple;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Description:
 * Created by nijunyang on 2022/1/19 10:01
 */
public class StreamWorkCount {

    public static void main(String[] args) throws Exception {
        //创建流处理执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置并行度，默认值当前计算机的CPU逻辑核数
        env.setMaxParallelism(8);
        // 从socket中读取数据  nc -lk 11111
        DataStream<String> inputDataStream = env.socketTextStream("106.12.139.147", 11111);
        //基于数据流进行转换计算
        DataStream<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new FlatMapper())
                .keyBy(item -> item.f0)
                .sum("f1")
                .returns(Types.TUPLE(Types.STRING, Types.INT));
        resultStream.print();
//        resultStream.writeAsText()
//        resultStream.writeAsCsv()
//        resultStream.writeToSocket()
        //执行任务
        env.execute();
    }


    public static class FlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>> {

        private static final long serialVersionUID = -7793341554119200739L;

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> out) throws Exception {
            String[] words = s.split(" ");
            for (String str : words) {
                out.collect(new Tuple2<>(str, 1));
            }
        }
    }
}
