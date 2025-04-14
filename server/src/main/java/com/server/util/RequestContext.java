package com.server.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.business.auth.domain.vo.LoginVo;
import com.server.config.redis.RedisPrefix;
import com.server.exception.BusinessException;
import com.server.pojo.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description: 请求上下文
 * @Author: yjy
 * @Date: 2024/12/8 16:41
 */
@Component
public class RequestContext {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final ThreadLocal<LoginVo> userInfo = new ThreadLocal<>();


    /**
     * 获取当前登录用户
     *
     * @return User
     */
    public LoginVo getUser() {
        return userInfo.get();
    }


    /**
     * 放入当前登录对象 LoginVo 至当前请求线程上下文中
     * 可能抛出 BusinessException(ResultCodeEnum.AuthError)
     *
     * @param redisPrefix redis 前缀
     * @param token token
     * @return LoginVo
     */
    public LoginVo setLoginVoByToken(String redisPrefix, String token) {
        String tokenFormRedis = redisTemplate.opsForValue().get(redisPrefix + token);
        LoginVo vo;
        try {
            vo = objectMapper.readValue(tokenFormRedis, LoginVo.class);
            if (vo == null || !StringUtils.hasText(vo.getToken()))
                throw new BusinessException(ResultCodeEnum.AuthError);
        } catch (Exception e) {
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
        try {
            userInfo.set(vo);
        } catch (Exception e) {
            userInfo.remove();
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
        return vo;
    }

    public LoginVo userLoginCheck() {
        return setLoginVoByToken(RedisPrefix.USER_TOKEN, getToken());
    }

    public LoginVo adminLoginCheck() {
        return setLoginVoByToken(RedisPrefix.ADMIN_TOKEN, getToken());
    }

    public void removeCurrentUser() {
        userInfo.remove();
    }

    public static String getToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
        // 提取 Bearer 后面的内容
        return authorization.substring("Bearer ".length()).trim();
    }

}
