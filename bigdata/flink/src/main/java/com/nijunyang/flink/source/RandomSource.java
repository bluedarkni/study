package com.nijunyang.flink.source;

import com.nijunyang.flink.model.Event;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.time.Instant;
import java.util.Random;

/**
 * Description: 随机数据源
 * Created by nijunyang on 2023/2/5 11:21
 */
public class RandomSource implements SourceFunction<Event> {

    private static final long serialVersionUID = -747034068087301932L;
    private boolean collecting = true;

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        Random random = new Random();
        String[] triggers = {"Mary", "Alice", "Jack"};
        String[] eventNames = {"clickIndex", "viewDetail", "ReceiveAward"};

        while (collecting) {
            sourceContext.collect(new Event(
                    triggers[random.nextInt(triggers.length)],
                    eventNames[random.nextInt(eventNames.length)],
                    Instant.now()));
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        collecting = false;
    }
}
