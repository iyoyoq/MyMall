package com.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/22 10:59
 */
@Slf4j
public class JsonUtil {

    public static final ObjectMapper objectMapper = SpringContextUtil.getBean(ObjectMapper.class);

    public static <T> String getJson(T origin) {
        try {
            return objectMapper.writeValueAsString(origin);
        } catch (JsonProcessingException e) {
            log.error("20250422110559 Object->json 转换异常");
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("20250422110800 json->Object 转换异常");
            throw new RuntimeException(e);
        }
    }

}
