package com.mickyli.smart4j.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * json操作工具类
 * Created by liqian on 2017/8/1.
 */
public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * obj转json
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("obj to json failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * json转obj
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> type){
        T obj;
        try {
            obj = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("json to obj failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }
}
