package cn.beforedark.common.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性类
 * @author yjy
 * @date 2025/7/6
 */
@Data
@Component
@ConfigurationProperties(prefix = "mymall.jwt")
public class JwtProperties {

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 过期时间（毫秒）
     */
    private long expiration = 86400000; // 默认24小时

}
