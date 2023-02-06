package com.nijunyang.flink.simple;

import com.nijunyang.flink.model.Event;
import com.nijunyang.flink.source.CustomSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Description: 自定义数据源
 * Created by nijunyang on 2022/12/16 17:31
 */
public class SourceCustom {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Event> dataStreamSource = env.addSource(new CustomSource());
        dataStreamSource.print();
        env.execute();

    }
}
