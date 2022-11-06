package com.nijunyang.mongo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Description:
 * Created by nijunyang on 2020/7/17 11:41
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "my.mongo")
public class MyMultipleMongoProperties {

    List<MyMongoProperties> myMongoProperties;
}
