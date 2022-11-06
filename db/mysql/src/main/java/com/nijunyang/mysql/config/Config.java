package com.nijunyang.mysql.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nijunyang.mysql.util.InstantCustomDeserializer;
import com.nijunyang.mysql.util.InstantCustomSerializer;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 * Created by nijunyang on 2020/2/4 18:51
 */
@Component
public class Config {

//    @Bean
    public ObjectMapper serializingObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        module.addSerializer(Instant.class, new InstantCustomSerializer<>(dateTimeFormatter));

        module.addDeserializer(Instant.class, new InstantCustomDeserializer());

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        return objectMapper;

    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configurationCustomizer -> {
            TypeHandlerRegistry typeHandlerRegistry = configurationCustomizer.getTypeHandlerRegistry();
            typeHandlerRegistry.register(EnumListHandler.class);
        };
    }
}
