package com.nijunyang.flink.window;

import com.nijunyang.flink.model.Event;
import com.nijunyang.flink.util.StreamUtil;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Description: 滑动时间窗口
 * Created by nijunyang on 2023/1/7 11:26
 */
public class SlidingWindow {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000

        StreamUtil.getAndPreProcessStream(env)
                .assignTimestampsAndWatermarks(
                        //顺序流
                        WatermarkStrategy.<Event>forMonotonousTimestamps()
                                .withTimestampAssigner(
                                        (element, recordTimestamp) -> element.timestamp.toEpochMilli()))
                .keyBy(event -> event.trigger)
                .window(SlidingEventTimeWindows.of(Time.seconds(5), Time.seconds(2))) //5s滑动窗口 2s滑一次
                .process(new CustomProcessWindow()) // 窗口处理函数
                .print();
        env.execute();
    }
}
