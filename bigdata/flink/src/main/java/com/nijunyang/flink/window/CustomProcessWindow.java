package com.nijunyang.flink.window;

import com.nijunyang.flink.model.Event;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Description: 自定义窗口处理函数
 * Created by nijunyang on 2023/1/6 16:26
 */
public class CustomProcessWindow extends ProcessWindowFunction<Event, String, String, TimeWindow> {
    private static final long serialVersionUID = -1737871524878286600L;


    @Override
    public void process(String key, ProcessWindowFunction<Event, String, String, TimeWindow>.Context context,
                        Iterable<Event> elements, Collector<String> out) throws Exception {
        long start = context.window().getStart();
        long end = context.window().getEnd();
        long currentWatermark = context.currentWatermark();
        long count = elements.spliterator().getExactSizeIfKnown();
        String s = key + "窗口时间：(" + start + "," + end + "],窗口数据量：" + count + ",水位线：" + currentWatermark;
        out.collect(s);
//            this.getRuntimeContext().getExecutionConfig().getParallelism();
    }
}
