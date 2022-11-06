package com.nijunyang.mongo.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by nijunyang on 2020/4/30 15:17
 */
@Component
public class MongoTemplatePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equalsIgnoreCase("mongoTemplate")) {
            MongoTemplate template = (MongoTemplate) bean;
            //去掉"_class"
            ((MappingMongoConverter)template.getConverter()).setTypeMapper(new DefaultMongoTypeMapper(null));
        }
        if (bean instanceof MongoTemplate) {
            MongoTemplate template = (MongoTemplate) bean;
            //去掉"_class"
            ((MappingMongoConverter)template.getConverter()).setTypeMapper(new DefaultMongoTypeMapper(null));
        }
        return bean;
    }
}
