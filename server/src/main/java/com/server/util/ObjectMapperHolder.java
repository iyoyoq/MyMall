package com.server.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description Jackson ObjectMapper
 * @Author: yjy
 * @Date: 2024/12/11 20:38
 */
public class ObjectMapperHolder {
    // 线程安全的 可复用
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
