package com.nijunyang.flink.window;

import com.nijunyang.flink.model.Event;
import com.nijunyang.flink.util.StreamUtil;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;

/**
 * Description: 滚动窗口
 * Created by nijunyang on 2022/12/20 11:32
 */
public class TumblingWindow {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000

        StreamUtil.getAndPreProcessStream(env)
                .assignTimestampsAndWatermarks(
                        //乱序流延迟5s
                        WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                                .withTimestampAssigner(
                                        (element, recordTimestamp) -> element.timestamp.toEpochMilli()))
                .keyBy(event -> event.trigger)
                .window(TumblingEventTimeWindows.of(Time.seconds(10))) //10s 滚动窗口
                .process(new CustomProcessWindow()) // 窗口处理函数
                .print();
        env.execute();
    }
}
