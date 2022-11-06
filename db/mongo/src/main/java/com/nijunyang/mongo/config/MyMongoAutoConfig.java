package com.nijunyang.mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mapping.model.CamelCaseAbbreviatingFieldNamingStrategy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * Created by nijunyang on 2020/7/17 14:46
 */
@Configuration
@EnableConfigurationProperties(MyMultipleMongoProperties.class)
@ConditionalOnProperty(name = "my.mongo.my-mongo-properties.mapping")
@ConfigurationProperties(prefix = "my.mongo.mapping")
@Setter
public class MyMongoAutoConfig {

    private Map<String, String> myMapping;

    private final MyMultipleMongoProperties multipleMongoProperties;

    @Autowired
    public MyMongoAutoConfig(MyMultipleMongoProperties multipleMongoProperties) {
        this.multipleMongoProperties = multipleMongoProperties;
    }

    @Bean
    public MyMongoTemplates userMongoTemplates() {
        ConcurrentHashMap<String, MongoTemplate> codeMongoTemplates = new ConcurrentHashMap<>(
                multipleMongoProperties.getMyMongoProperties().size());
        multipleMongoProperties.getMyMongoProperties().forEach(myMongoProperties -> {
            if (!myMapping.containsKey(myMongoProperties.getName())) {
                return;
            }
            String[] keys = myMapping.get(myMongoProperties.getName()).split(",");
            MongoDbFactory factory = mongoDbFactory(myMongoProperties);
            MappingMongoConverter converter = mappingMongoConverter(factory);
            MongoTemplate mongoTemplate = new MongoTemplate(factory, converter);
            for (String key : keys) {
                codeMongoTemplates.put(key, mongoTemplate);
            }
        });
        return new MyMongoTemplates(codeMongoTemplates);
    }

    @Bean
    @ConditionalOnBean(MyMongoTemplates.class)
    public MongoService mongoUserService(MyMongoTemplates myMongoTemplates) {
        return new MongoService(myMongoTemplates);
    }

    /**
     * 依据配置生成mongoDbFactory
     *
     * @param myMongoProperties 配置
     * @return MongoDbFactory
     */
    private MongoDbFactory mongoDbFactory(MyMongoProperties myMongoProperties) {
        // mongo url+port
        ServerAddress serverAddress = new ServerAddress(myMongoProperties.getHost(), myMongoProperties.getPort());
        // mongo 认证信息
        MongoCredential mongoCredential = MongoCredential.createCredential(myMongoProperties.getUsername(),
                myMongoProperties.getAdminDataBase(), myMongoProperties.getPassword().toCharArray());
        // mongo 配置信息
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                .connectTimeout(myMongoProperties.getConnectTimeout())
                .maxConnectionIdleTime(myMongoProperties.getMaxConnectionIdleTime())
                .sslEnabled(myMongoProperties.isSslEnabled())
                .connectionsPerHost(myMongoProperties.getConnectionsPerHost())
                .socketTimeout(myMongoProperties.getSocketTimeout())
                .sslInvalidHostNameAllowed(myMongoProperties.isSslInvalidHostNameAllowed()).build();
        return new SimpleMongoDbFactory(new MongoClient(serverAddress, mongoCredential, mongoClientOptions),
                myMongoProperties.getDatabase());

    }

    /**
     * 去除_class
     *
     * @param factory mongoDbFactory
     * @return mapping
     */
    private MappingMongoConverter mappingMongoConverter(MongoDbFactory factory) {
        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        mongoMappingContext.setFieldNamingStrategy(new CamelCaseAbbreviatingFieldNamingStrategy());
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
}
