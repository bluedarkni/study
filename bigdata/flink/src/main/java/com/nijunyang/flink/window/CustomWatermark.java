package com.nijunyang.flink.window;

import com.nijunyang.flink.model.Event;
import com.nijunyang.flink.source.SourceWithWatermark;
import com.nijunyang.flink.util.StreamUtil;
import org.apache.flink.api.common.eventtime.TimestampAssigner;
import org.apache.flink.api.common.eventtime.TimestampAssignerSupplier;
import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Description: 自定义水位线
 * Created by nijunyang on 2022/12/20 10:40
 */
public class CustomWatermark {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //1. 自定义水位线策略
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000
        StreamUtil.getAndPreProcessStream(env)
                .assignTimestampsAndWatermarks(new CustomWatermarkStrategy())
                .print();
        //2.数据源上设置水位线
        env.addSource(new SourceWithWatermark()).print();
        env.execute();

    }

    /**
     * 水位线策略
     */
    public static class CustomWatermarkStrategy implements WatermarkStrategy<Event> {

        private static final long serialVersionUID = 1732297949593206799L;

        @Override
        public WatermarkGenerator<Event> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
            //水位线生成器
            return new CustomPeriodicGenerator();
        }

        @Override
        public TimestampAssigner<Event> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
            //提取时间戳
            return (element, recordTimestamp) -> element.timestamp.toEpochMilli();
        }
    }


    /**
     * 水位线生成器
     */
    public static class CustomPeriodicGenerator implements WatermarkGenerator<Event> {
        /**
         * 延迟时间
         */
        private final Long delayTime = 5000L;
        /**
         * 已到达的最大时间
         */
        private Long maxTs = Long.MIN_VALUE + delayTime + 1L;

        /**
         * 每条数据调用一次
         * @param event
         * @param eventTimestamp
         * @param output
         */
        @Override
        public void onEvent(Event event, long eventTimestamp, WatermarkOutput output) {
            maxTs = Math.max(event.timestamp.toEpochMilli(), maxTs);
        }

        /**
         * 发送水位线  默认200ms调用一次
         * @param output
         */
        @Override
        public void onPeriodicEmit(WatermarkOutput output) {
            //水位线 = 已到达的最大时间-延迟时间，相当于把当前时间调慢了
            //比如9:00数据已经到了,然后把时调慢1分钟相当于当前时间8:59，这样9:00点发车实际上真实时间是9:01
            output.emitWatermark(new Watermark(maxTs - delayTime - 1L));
        }

    }
}
