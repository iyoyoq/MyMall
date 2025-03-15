package com.server.config.jackson;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @Description: 基于Jackson的日期格式配置
 * @Author: yjy
 * @Date: 2025/3/15 9:46
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DateConverterProperties.class)
public class MyDateConverterConfig {

    @Autowired
    private DateConverterProperties props;

    /**
     * ObjectMapper 是全局注入的，
     * 它会受 Jackson2ObjectMapperBuilderCustomizer 这个 @Bean 配置的影响，
     * 也就是说，你自定义的日期格式、时区、序列化/反序列化规则会应用到整个 Spring 容器中的 ObjectMapper。
     *     使用示例：
     *     @Autowired
     *     private ObjectMapper objectMapper;
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        // log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        // log.info("@================= 初始化jackson日期序列化 ==================@");
        // log.info("@==========================================================@");
        // log.info("@========== Locale : 【{}】 ============================@", props.getLocale());
        // log.info("@========== TimeZone : 【{}】 ======================@", props.getTimeZone().getDisplayName());
        // log.info("@========== Date : 【{}】 =================@", props.getDateTimeFormat());
        // log.info("@========== LocalDateTime : 【{}】 ========@", props.getDateTimeFormat());
        // log.info("@========== LocalDate : 【{}】 =====================@", props.getLocalDateFormat());
        // log.info("@========== LocalTime : 【{}】 =======================@", props.getLocalTimeFormat());
        // log.info("@==========================================================@");
        // log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        final DateTimeFormatter dateTimeFormatter = this.ofPattern(props.getDateTimeFormat());
        final DateTimeFormatter localDateFormatter = this.ofPattern(props.getLocalDateFormat());
        final DateTimeFormatter localTimeFormatter = this.ofPattern(props.getLocalTimeFormat());
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(props.getDateTimeFormat());

        return builder -> {
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    // 地区
                    .locale(props.getLocale())
                    // 时区
                    .timeZone(props.getTimeZone());
            // 序列化
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter))
                    .serializerByType(LocalDate.class, new LocalDateSerializer(localDateFormatter))
                    .serializerByType(LocalTime.class, new LocalTimeSerializer(localTimeFormatter))
                    .serializerByType(Date.class, new DateSerializer(false, simpleDateFormat));
            // 反序列化
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter))
                    .deserializerByType(LocalDate.class, new LocalDateDeserializer(localDateFormatter))
                    .deserializerByType(LocalTime.class, new LocalTimeDeserializer(localTimeFormatter))
                    .deserializerByType(Date.class,
                            new DateDeserializers.DateDeserializer(DateDeserializers.DateDeserializer.instance,
                                    simpleDateFormat, props.getDateTimeFormat()));
        };
    }


    /**
     * 根据字符串 获取 DateTimeFormatter
     *
     * @param pattern 日期格式字符串
     * @return DateTimeFormatter
     */
    private DateTimeFormatter ofPattern(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }


    /**
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(props.getLocalDateFormat()));
            }
        };
    }

    /**
     * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(props.getDateTimeFormat()));
            }
        };
    }

    /**
     * LocalTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateTimeFormatter.ofPattern(props.getLocalTimeFormat()));
            }
        };
    }

    /**
     * Date转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(props.getDateTimeFormat());

                try {
                    return simpleDateFormat.parse(source);
                } catch (ParseException e) {
                    log.error(">>>> init Converter String to Date error!", e);
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
