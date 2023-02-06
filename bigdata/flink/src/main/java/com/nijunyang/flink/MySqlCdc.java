package com.nijunyang.flink;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.ververica.cdc.connectors.mysql.MySQLSource;
import com.alibaba.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.alibaba.ververica.cdc.debezium.DebeziumDeserializationSchema;
import com.alibaba.ververica.cdc.debezium.DebeziumSourceFunction;
import io.debezium.data.Envelope;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.List;
import java.util.Properties;

/**
 * Description:
 * Created by nijunyang on 2023/2/4 19:37
 */
public class MySqlCdc {

    public static void main(String[] args) throws Exception {
//        MySqlSource<String> mysqlSource = MySqlSource.<String>builder()
//                .hostname("127.0.0.1")
//                .port(3306)
//                .databaseList("mytest") // monitor all tables under inventory database
//                .tableList("mytest.user_action") // set captured table
//                .username("root")
//                .password("root")
//                .deserializer(new JsonDebeziumDeserializationSchema()) // converts SourceRecord to JSON String
//                .build();

        Properties properties = new Properties();
        // 配置 Debezium在初始化快照的时候（扫描历史数据的时候） =》 不要锁表
        properties.setProperty("debezium.snapshot.locking.mode", "none");
        /**
         * 控制连接器是否持有全局 MySQL 读锁以及持有多长时间，这会在连接器执行快照时阻止对数据库的任何更新。可能的设置是：
         *
         * minimal(默认)- 连接器仅对快照的初始部分持有全局读锁，在此期间连接器读取数据库模式和其他元数据。快照中的剩余工作涉及从每个表中选择所有行。连接器可以通过使用 REPEATABLE READ 事务以一致的方式执行此操作。即使不再持有全局读锁并且其他 MySQL 客户端正在更新数据库时也是如此。
         *
         * minimal_percona- 连接器持有全局备份锁仅适用于连接器读取数据库模式和其他元数据的快照的初始部分。快照中的剩余工作涉及从每个表中选择所有行。连接器可以通过使用 REPEATABLE READ 事务以一致的方式执行此操作。即使不再持有全局备份锁并且其他 MySQL 客户端正在更新数据库时也是如此。此模式不会将表刷新到磁盘，不会被长时间运行的读取阻塞，并且仅在 Percona Server 中可用。
         *
         * extended- 在快照期间阻止所有写入。如果有客户端正在提交 MySQL 从 REPEATABLE READ 语义中排除的操作，请使用此设置。
         *
         * none- 防止连接器在快照期间获取任何表锁。虽然所有快照模式都允许使用此设置，但当且仅当快照运行时没有架构更改时才可以安全使用。对于使用 MyISAM 引擎定义的表，尽管在 MyISAM 获取表锁时设置了此属性，但表仍将被锁定。此行为不同于 InnoDB 引擎，后者获取行级锁。
         */
        DebeziumSourceFunction<String> mysqlSource = MySQLSource.<String>builder()
                .hostname("127.0.0.1")
                .port(3306)
                .deserializer(new MyDeserializationSchema())
                .username("root")
                .password("root")
                .databaseList("mytest") // 指定某个特定的库
                .tableList("mytest.user_action") //指定特定的表
                .startupOptions(StartupOptions.initial())// 快照策略 这个启动选项有五种，是否处理历史的
                .debeziumProperties(properties).build(); //配置不要锁表 但是数据一致性不是精准一次 会变成最少一次


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // enable checkpoint
        env.enableCheckpointing(60000);  //checkpoint需要什么条件?com/ververica/cdc/connectors/mysql/source/enumerator/MySqlSourceEnumerator.snapshotState()
        env.addSource(mysqlSource)
//                env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source")
                // set 4 parallel source tasks
                .setParallelism(1)
                .print("最终数据===>").setParallelism(1); // use parallelism 1 for sink to keep message ordering

        env.execute("mysql-cdc");
    }

    public static class MyDeserializationSchema implements DebeziumDeserializationSchema<String> {

        private static final long serialVersionUID = 516579147020299092L;

        @Override
        public void deserialize(SourceRecord sourceRecord, Collector<String> collector) throws Exception {
            Struct value = (Struct) sourceRecord.value();
            Struct after = value.getStruct("after");
            Struct source = value.getStruct("source");

            String db = source.getString("db");//库名
            String table = source.getString("table");//表名

            //获取操作类型 直接将参数穿进去 会自己解析出来 里面是个enum对应每个操作
            /* READ("r"),
               CREATE("c"),
                UPDATE("u"),
                DELETE("d");*/
            Envelope.Operation operation = Envelope.operationFor(sourceRecord);
            String opstr = operation.toString().toLowerCase();
            //类型修正 会把insert识别成create
            if (opstr.equals("create")) {
                opstr = "insert";
            }

            //获取after结构体里面的表数据，封装成json输出
            JSONObject json1 = new JSONObject();
            JSONObject json2 = new JSONObject();
            //加个判空
            if (after != null) {
                List<Field> data = after.schema().fields(); //获取结构体
                for (Field field : data) {
                    String name = field.name(); //结构体的名字
                    Object value2 = after.get(field);//结构体的字段值
                    //放进json2里面去 json2放到json1里面去
                    json2.put(name, value2);
                }
            }


            //整理成大json串输出
            json1.put("db", db);
            json1.put("table", table);
            json1.put("data", json2);
            json1.put("type", opstr);

            collector.collect(json1.toJSONString());

        }

        @Override
        public TypeInformation<String> getProducedType() {
            return TypeInformation.of(String.class);
        }

    }
}
