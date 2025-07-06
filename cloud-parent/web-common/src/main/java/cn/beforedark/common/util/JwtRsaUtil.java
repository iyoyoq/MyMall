package cn.beforedark.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;

/**
 * JWT RSA-256 加密解密工具类
 *
 * @author yjy
 * @date 2025/7/6
 */
@Slf4j
public class JwtRsaUtil {

    /**
     * 生成RSA密钥对
     *
     * @return KeyPair对象
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // 推荐使用2048位密钥
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("生成RSA密钥对失败", e);
        }
    }

    /**
     * 获取公钥字符串
     *
     * @param keyPair 密钥对
     * @return Base64编码的公钥字符串
     */
    public static String getPublicKeyString(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        return Base64Utils.encodeToString(publicKey.getEncoded());
    }

    /**
     * 获取私钥字符串
     *
     * @param keyPair 密钥对
     * @return Base64编码的私钥字符串
     */
    public static String getPrivateKeyString(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        return Base64Utils.encodeToString(privateKey.getEncoded());
    }

    /**
     * 从字符串还原公钥
     *
     * @param publicKeyString Base64编码的公钥字符串
     * @return 公钥对象
     */
    public static PublicKey getPublicKey(String publicKeyString) {
        try {
            byte[] keyBytes = Base64Utils.decodeFromString(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("还原公钥失败", e);
        }
    }

    /**
     * 从字符串还原私钥
     *
     * @param privateKeyString Base64编码的私钥字符串
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String privateKeyString) {
        try {
            byte[] keyBytes = Base64Utils.decodeFromString(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            log.error("还原私钥失败", e);
            throw new RuntimeException("还原私钥失败", e);
        }
    }

    /**
     * 使用私钥生成JWT令牌
     *
     * @param claims       JWT的声明
     * @param privateKey   私钥
     * @param expirationMs 过期时间（毫秒）
     * @return JWT令牌
     */
    public static String generateToken(Map<String, Object> claims, PrivateKey privateKey, long expirationMs) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 使用公钥验证并解析JWT令牌
     *
     * @param token     JWT令牌
     * @param publicKey 公钥
     * @return JWT声明
     */
    public static Claims parseToken(String token, PublicKey publicKey) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 使用私钥字符串生成JWT令牌
     *
     * @param claims           JWT的声明
     * @param privateKeyString Base64编码的私钥字符串
     * @param expirationMs     过期时间（毫秒）
     * @return JWT令牌
     */
    public static String generateToken(Map<String, Object> claims, String privateKeyString, long expirationMs) {
        PrivateKey privateKey = getPrivateKey(privateKeyString);
        return generateToken(claims, privateKey, expirationMs);
    }

    /**
     * 使用公钥字符串验证并解析JWT令牌
     *
     * @param token           JWT令牌
     * @param publicKeyString Base64编码的公钥字符串
     * @return JWT声明
     */
    public static Claims parseToken(String token, String publicKeyString) {
        PublicKey publicKey = getPublicKey(publicKeyString);
        return parseToken(token, publicKey);
    }

    /**
     * 生成并打印RSA密钥对
     * 可以在应用启动时调用此方法生成密钥对，然后配置到application.yml中
     */
    public static void generateAndPrintKeyPair() {
        KeyPair keyPair = generateKeyPair();
        String publicKeyString = getPublicKeyString(keyPair);
        String privateKeyString = getPrivateKeyString(keyPair);

        System.out.println("公钥: " + publicKeyString);
        System.out.println("私钥: " + privateKeyString);
    }

}
