package cn.beforedark.servicegateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/7/5 19:25
 */
@Data
@ConfigurationProperties(prefix = "mymall.filter-path")
@Component
public class FilterPathProperties {
    private List<String> includePath;
    private List<String> excludePath;
}
