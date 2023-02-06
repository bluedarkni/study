package com.nijunyang.flink.source;

import com.nijunyang.flink.model.Event;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;

import java.time.Instant;

/**
 * Description:
 * Created by nijunyang on 2022/12/20 11:18
 */
public class SourceWithWatermark implements SourceFunction<Event> {

    private static final long serialVersionUID = -7560215154052314624L;
    private boolean collecting = true;

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        while (collecting) {
            //收集数据
            Event event = new Event();
            event.timestamp = Instant.now();
            //收集数据并指明时间戳
            sourceContext.collectWithTimestamp(event, event.timestamp.toEpochMilli());
            //发送水位线
            sourceContext.emitWatermark(new Watermark(event.timestamp.toEpochMilli() - 1L));
            Thread.sleep(1000L);
        }
    }

    @Override
    public void cancel() {
        collecting = false;
    }
}
