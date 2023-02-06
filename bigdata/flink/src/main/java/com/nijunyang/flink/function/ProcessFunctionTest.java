package com.nijunyang.flink.function;

import com.alibaba.fastjson2.JSON;
import com.nijunyang.flink.model.Event;
import com.nijunyang.flink.source.RandomSource;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

/**
 * Description:
 * Created by nijunyang on 2023/2/5 11:14
 */
public class ProcessFunctionTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.addSource(new RandomSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forMonotonousTimestamps().withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event event, long l) {
                                return event.timestamp.toEpochMilli();
                            }
                        })
                ).process(new ProcessFunction<Event, String>() {
                    //处理每一个元素
                    @Override
                    public void processElement(Event event, ProcessFunction<Event, String>.Context context, Collector<String> collector) throws Exception {
                        collector.collect(JSON.toJSONString(event));
                        System.out.println("当前时间戳:" + context.timestamp());
                        System.out.println("处理时间:" + context.timerService().currentProcessingTime());
                        System.out.println("当前水位线:" + context.timerService().currentWatermark());
//                        context.timerService().registerEventTimeTimer(1000L); 定时器只能在keyedStream中设置,需要keyBy
                    }
                })
                .print();
        env.execute();
    }
}
