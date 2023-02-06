package com.nijunyang.flink.window;

import com.nijunyang.flink.model.Event;
import com.nijunyang.flink.util.StreamUtil;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;

import java.time.Duration;

/**
 * Description: 迟到数据处理
 * Created by nijunyang on 2022/12/20 16:13
 */
public class LateData {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000

        SingleOutputStreamOperator<Event> stream = StreamUtil.getAndPreProcessStream(env)
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ofSeconds(2)) //乱序延迟2s
                        .withTimestampAssigner((element, recordTimestamp) -> element.timestamp.toEpochMilli()));

        // 定义侧输出流
        OutputTag<Event> outputTag = new OutputTag<Event>("late"){};
        SingleOutputStreamOperator<String> operator = stream.keyBy(Event::getTrigger)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))//
                .sideOutputLateData(outputTag)  //延迟数据输入到侧输出流
                .process(new CustomProcessWindow());
        operator.print("main");
        operator.getSideOutput(outputTag).print("late");//处理测输出流数据
        env.execute();


    }
}
