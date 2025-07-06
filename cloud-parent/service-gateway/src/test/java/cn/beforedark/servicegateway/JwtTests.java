package cn.beforedark.servicegateway;

import cn.beforedark.common.config.jwt.JwtProperties;
import cn.beforedark.common.util.JwtRsaUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/7/6 20:56
 */

@SpringBootTest(classes = ServiceGatewayApplication.class)
public class JwtTests {
    @Autowired
    JwtProperties jwtProperties;

    @Test
    void testGen() {
        JwtRsaUtil.generateAndPrintKeyPair();
    }

    @Test
    void test() {
        String privateKey = jwtProperties.getPrivateKey();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "admin");
        claims.put("userId", "123456");
        String s = JwtRsaUtil.generateToken(claims, privateKey, 86400000);
        System.out.println("生成的jwt为：\n" + s + "\n");
        System.out.println("公钥为：\n" + jwtProperties.getPublicKey() + "\n");
        System.out.println("解密为：\n" + JwtRsaUtil.parseToken(s, jwtProperties.getPublicKey()));
    }
}
