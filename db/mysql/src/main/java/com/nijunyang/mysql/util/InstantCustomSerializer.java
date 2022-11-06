package com.nijunyang.mysql.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 * Created by nijunyang on 2020/2/4 18:33
 */
public class InstantCustomSerializer<T> extends JsonSerializer<Instant> {
    private DateTimeFormatter format;

    public InstantCustomSerializer(DateTimeFormatter formatter) {
        this.format = formatter;
    }

    @Override
    public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (instant == null) {
            return;
        }
        String jsonValue = format.format(instant.atZone(ZoneId.systemDefault()));
        jsonGenerator.writeString(jsonValue);
    }
}