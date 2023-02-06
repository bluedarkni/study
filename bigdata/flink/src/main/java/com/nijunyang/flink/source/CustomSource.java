package com.nijunyang.flink.source;

import com.nijunyang.flink.model.Event;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * Description: 自定义数据源
 * Created by nijunyang on 2022/12/16 17:32
 */
public class CustomSource implements SourceFunction<Event> {

    private static final long serialVersionUID = -747034068087301932L;
    private boolean collecting = true;

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        while (collecting) {
            //收集数据
            Event event = new Event();
            sourceContext.collect(event);
        }
    }

    @Override
    public void cancel() {
        collecting = false;
    }
}
