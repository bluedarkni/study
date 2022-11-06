package com.nijunyang.mongo.config;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * Created by nijunyang on 2020/7/17 14:27
 */
public class MyMongoTemplates {

    private final static String DEFAULT_KEY = "default";

    private final ConcurrentHashMap<String, MongoTemplate> myMongoProperties;

    public MyMongoTemplates(ConcurrentHashMap<String, MongoTemplate> myMongoProperties) {
        this.myMongoProperties = myMongoProperties;
    }

    public MongoTemplate getTemplate(String key){
        MongoTemplate mongoTemplate =  myMongoProperties.get(key);
        if(mongoTemplate == null) {
            return myMongoProperties.get(DEFAULT_KEY);
        } else {
            return mongoTemplate;
        }
    }


}
