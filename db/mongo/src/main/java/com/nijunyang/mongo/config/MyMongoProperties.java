package com.nijunyang.mongo.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by nijunyang on 2020/7/17 11:22
 */
@Getter
@Setter
public class MyMongoProperties {
    /**
     * 数据源名称
     */
    private String name;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 数据库名称
     */
    private String database;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接超时时间，单位：毫秒
     */
    private Integer connectTimeout;

    /**
     * 最大空闲时间，单位：毫秒
     */
    private Integer maxConnectionIdleTime;

    private boolean sslEnabled;

    private boolean sslInvalidHostNameAllowed;

    /**
     * 认证数据库
     */
    private String adminDataBase;

    private int connectionsPerHost = 600;

    private int socketTimeout = 5000;
}
