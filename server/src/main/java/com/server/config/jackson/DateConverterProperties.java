package com.server.config.jackson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.TimeZone;

@Data
@ConfigurationProperties(prefix = "jackson")
public class DateConverterProperties {

    /**
     * Date/LocalDateTime 格式化
     */
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * localDate 格式化
     */
    private String localDateFormat = "yyyy-MM-dd";

    /**
     * localTime 格式化
     */
    private String localTimeFormat = "HH:mm:ss";

    /**
     * 时区, 默认 GMT+8
     */
    private TimeZone timeZone = TimeZone.getTimeZone("GMT+8");

    /**
     * 地区, 默认 zh_CN
     */
    private Locale locale = Locale.CHINA;

}
