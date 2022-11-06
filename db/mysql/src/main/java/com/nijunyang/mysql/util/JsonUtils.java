package com.nijunyang.mysql.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Description:
 * Created by nijunyang on 2019/12/8 0:51
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER;

    static  {
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        module.addSerializer(Instant.class, new InstantCustomSerializer<>(dateTimeFormatter));
        module.addDeserializer(Instant.class, new InstantCustomDeserializer());
        MAPPER = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();

        MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MAPPER.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
        MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        MAPPER.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
    }

    /**
     * 将Object对象转换为JSON格式的字符串
     *
     * @param obj 要转换成JSON格式字符串的对象
     * @return 序列化成功，返回json格式的字符串；否则返回null
     */
    public static String write2JsonString(Object obj) {
        if (obj != null) {
            try {
                return MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 将JSON格式的字符串转换为期望类型的对象
     *
     * @param jsonString JSON格式的字符串
     * @param clazz      要转换的目标对象类型；当不存在具体的实体类时，可传入JsonNode.class，
     *                   将JSON字符串转换为一个JsonNode对象
     * @return 反序列化成功，返回T类型的对象；否则返回null
     */
    public static <T> T readJson2Entity(String jsonString, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new RuntimeException("readJson2Entity error");
        }
    }

    /**
     * 将JSON格式的字符串转换为期望类型的对象
     *
     * @param jsonString JSON格式的字符串
     * @param clazz      要转换的目标对象类型
     * @return 反序列化成功，返回T类型的对象列表；否则返回null
     */
    public static <T> List<T> readJson2EntityList(String jsonString, Class<T> clazz) {
        JavaType javaType = MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return MAPPER.readValue(jsonString, javaType);
        } catch (IOException e) {
            throw new RuntimeException("readJson2Entity error");
        }
    }

    /**
     * 奖JSON格式的字符串转化为自定义类型的对象
     * @param jsonString JSON格式的字符串
     * @param valueTypeRef 要转换的目标对象类型
     * @param <T>  要转换的目标对象
     * @return 反序列化成功，返回T类型的对象列表；否则返回null
     */
    public static <T> T readJson2EntityByTypeReference(String jsonString, TypeReference<T> valueTypeRef) {

        T result = null;
        try {
            result = MAPPER.readValue(jsonString, valueTypeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
