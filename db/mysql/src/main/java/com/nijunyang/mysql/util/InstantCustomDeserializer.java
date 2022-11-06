package com.nijunyang.mysql.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

/**
 * Description:
 * Created by nijunyang on 2020/2/4 18:36
 */
public class InstantCustomDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String dateString = p.getText().trim();
        if(dateString != null && !dateString.isEmpty()){
            Date pareDate;
            try {
                pareDate = DateFormatUtil.pareDate(dateString);
                if(null != pareDate){
                    return pareDate.toInstant();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

