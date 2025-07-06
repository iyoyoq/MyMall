package cn.beforedark.common.util;

import cn.beforedark.common.config.spring.SpringContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/4/22 10:59
 */
@Slf4j
public class JsonUtil {

    public static final ObjectMapper objectMapper = SpringContextHolder.getApplicationContext().getBean(ObjectMapper.class);

    public static <T> String getJson(T origin) {
        try {
            return objectMapper.writeValueAsString(origin);
        } catch (JsonProcessingException e) {
            log.error("0706154158 Object->json 转换异常");
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("0706154201 json->Object 转换异常");
            throw new RuntimeException(e);
        }
    }

}
