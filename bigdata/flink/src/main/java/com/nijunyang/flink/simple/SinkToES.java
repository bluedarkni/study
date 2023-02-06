package com.nijunyang.flink.simple;

import com.nijunyang.flink.model.Event;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.flink.util.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 输出到es
 * Created by nijunyang on 2022/12/19 11:12
 */
public class SinkToES {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 从socket中读取数据  nc -lk 11111   jack,clickIndex,1000
        DataStream<String> inputDataStream = env.socketTextStream("192.168.0.67", 11111);
        SingleOutputStreamOperator<Event> streamOperator = inputDataStream
                .filter(s -> !StringUtils.isNullOrWhitespaceOnly(s) && s.split(",").length == 3)
                .map(s -> {
                    String[] split = s.split(",");
                    return new Event(split[0], split[1], Instant.ofEpochMilli(Long.parseLong(split[2])));
                });
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("192.168.0.67", 9200, "http"));

        ElasticsearchSinkFunction<Event> elasticsearchSinkFunction = new ElasticsearchSinkFunction<Event>() {

            private static final long serialVersionUID = -5816138613054092338L;

            @Override
            public void process(Event event, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {

                IndexRequest request = Requests.indexRequest()
                        .index("visit_data")
//                        .type("type") //Es6必须定义type
                        .source(event);

                requestIndexer.add(request);
            }
        };
        streamOperator.addSink(new ElasticsearchSink.Builder<>(httpHosts, elasticsearchSinkFunction).build());

        env.execute();
    }
}
