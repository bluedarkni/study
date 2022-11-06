package com.nijunyang.kafka.binlog;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author machengyuan 2018-9-13 下午10:31:14
 * @version 1.0.0
 */
public class FlatMessage implements Serializable {

    private static final long         serialVersionUID = -3386650678735860050L;
    private long                      id;
    //数据库
    private String                    database;
    //表
    private String                    table;

    private List<String>              pkNames;

    private Boolean                   isDdl;
    //ALTER, UPDATE
    private String                    type;

    // binlog executeTime
    private Long                      es;

    // dml build timeStamp
    private Long                      ts;
    //ddl 才有  row模式binlog没有
    private String                    sql;

    private Map<String, Integer>      sqlType;

    //字段对应的数据类型
    private Map<String, String>       mysqlType;
    //执行后的数据，表全字段map
    private List<Map<String, String>> data;
    //修改的字段,值
    private List<Map<String, String>> old;

    public FlatMessage() {
    }

    public FlatMessage(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getPkNames() {
        return pkNames;
    }

    public void addPkName(String pkName) {
        if (this.pkNames == null) {
            this.pkNames = Lists.newArrayList();
        }
        this.pkNames.add(pkName);
    }

    public void setPkNames(List<String> pkNames) {
        this.pkNames = pkNames;
    }

    public Boolean getIsDdl() {
        return isDdl;
    }

    public void setIsDdl(Boolean isDdl) {
        this.isDdl = isDdl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<String, Integer> getSqlType() {
        return sqlType;
    }

    public void setSqlType(Map<String, Integer> sqlType) {
        this.sqlType = sqlType;
    }

    public Map<String, String> getMysqlType() {
        return mysqlType;
    }

    public void setMysqlType(Map<String, String> mysqlType) {
        this.mysqlType = mysqlType;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public List<Map<String, String>> getOld() {
        return old;
    }

    public void setOld(List<Map<String, String>> old) {
        this.old = old;
    }

    public Long getEs() {
        return es;
    }

    public void setEs(Long es) {
        this.es = es;
    }

    @Override
    public String toString() {
        return "FlatMessage [id=" + id + ", database=" + database + ", table=" + table + ", isDdl=" + isDdl + ", type="
               + type + ", es=" + es + ", ts=" + ts + ", sql=" + sql + ", sqlType=" + sqlType + ", mysqlType="
               + mysqlType + ", data=" + data + ", old=" + old + "]";
    }

    /**
     INSERT:
     {"data":[{"id":"6","category_name":"安抚","icon_url":"213","un_icon_url":null,"channel_id":null,"label":null,"company_name":null,"company_id":null,"brand_name":null,"brand_id":null,"sort":null,"create_time":"2022-07-08 06:05:18","create_by":"","update_time":"2022-07-08 06:05:18","update_by":"","is_deleted":"0"}],"database":"rights_package","es":1657260318000,"id":3,"isDdl":false,"mysqlType":{"id":"bigint(20)","category_name":"varchar(64)","icon_url":"varchar(255)","un_icon_url":"varchar(255)","channel_id":"varchar(64)","label":"varchar(255)","company_name":"varchar(255)","company_id":"varchar(50)","brand_name":"varchar(255)","brand_id":"varchar(255)","sort":"tinyint(4)","create_time":"datetime","create_by":"varchar(50)","update_time":"datetime","update_by":"varchar(50)","is_deleted":"tinyint(1)"},"old":null,"pkNames":["id"],"sql":"","sqlType":{"id":-5,"category_name":12,"icon_url":12,"un_icon_url":12,"channel_id":12,"label":12,"company_name":12,"company_id":12,"brand_name":12,"brand_id":12,"sort":-6,"create_time":93,"create_by":12,"update_time":93,"update_by":12,"is_deleted":-6},"table":"category","ts":1657260318683,"type":"INSERT"}

     UPDATE：
     {"data":[{"id":"5","category_name":"456456","icon_url":"https://prod-img-1303824005.cos.ap-beijing.myqcloud.com/img/6f745ee7-ac14-479e-9ee4-e43048ed5133.png","un_icon_url":"e43048ed5133.png","channel_id":"C_MEMBER","label":null,"company_name":null,"company_id":null,"brand_name":null,"brand_id":null,"sort":"10","create_time":"2022-07-04 13:22:28","create_by":"","update_time":"2022-07-07 09:47:12","update_by":"","is_deleted":"0"}],"database":"rights_package","es":1657187232000,"id":1,"isDdl":false,"mysqlType":{"id":"bigint(20)","category_name":"varchar(64)","icon_url":"varchar(255)","un_icon_url":"varchar(255)","channel_id":"varchar(64)","label":"varchar(255)","company_name":"varchar(255)","company_id":"varchar(50)","brand_name":"varchar(255)","brand_id":"varchar(255)","sort":"tinyint(4)","create_time":"datetime","create_by":"varchar(50)","update_time":"datetime","update_by":"varchar(50)","is_deleted":"tinyint(1)"},"old":[{"channel_id":"njy-canal","update_time":"2022-07-07 09:47:02"}],"pkNames":["id"],"sql":"","sqlType":{"id":-5,"category_name":12,"icon_url":12,"un_icon_url":12,"channel_id":12,"label":12,"company_name":12,"company_id":12,"brand_name":12,"brand_id":12,"sort":-6,"create_time":93,"create_by":12,"update_time":93,"update_by":12,"is_deleted":-6},"table":"category","ts":1657245586560,"type":"UPDATE"}

     DELETE:
     {"data":[{"id":"6","category_name":"安抚","icon_url":"213","un_icon_url":null,"channel_id":null,"label":null,"company_name":null,"company_id":null,"brand_name":null,"brand_id":null,"sort":null,"create_time":"2022-07-08 06:05:18","create_by":"","update_time":"2022-07-08 06:05:18","update_by":"","is_deleted":"0"}],"database":"rights_package","es":1657260469000,"id":4,"isDdl":false,"mysqlType":{"id":"bigint(20)","category_name":"varchar(64)","icon_url":"varchar(255)","un_icon_url":"varchar(255)","channel_id":"varchar(64)","label":"varchar(255)","company_name":"varchar(255)","company_id":"varchar(50)","brand_name":"varchar(255)","brand_id":"varchar(255)","sort":"tinyint(4)","create_time":"datetime","create_by":"varchar(50)","update_time":"datetime","update_by":"varchar(50)","is_deleted":"tinyint(1)"},"old":null,"pkNames":["id"],"sql":"","sqlType":{"id":-5,"category_name":12,"icon_url":12,"un_icon_url":12,"channel_id":12,"label":12,"company_name":12,"company_id":12,"brand_name":12,"brand_id":12,"sort":-6,"create_time":93,"create_by":12,"update_time":93,"update_by":12,"is_deleted":-6},"table":"category","ts":1657260469283,"type":"DELETE"}

     ALTER:
     {"data":null,"database":"rights_package","es":1657177806000,"id":1,"isDdl":true,"mysqlType":null,"old":null,"pkNames":null,"sql":"ALTER TABLE `rights_package`.`base_data_id_mapping` \r\nADD COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' FIRST,\r\nADD PRIMARY KEY (`id`)","sqlType":null,"table":"base_data_id_mapping","ts":1657245586559,"type":"ALTER"}
     */
}
