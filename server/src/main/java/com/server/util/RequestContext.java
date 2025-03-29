package com.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.business.auth.domain.User;
import com.server.business.auth.domain.vo.UserLoginVO;
import com.server.config.redis.RedisPrefix;
import com.server.exception.BusinessException;
import com.server.pojo.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

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

    private final ThreadLocal<UserLoginVO> userInfo = new ThreadLocal<>();


    /**
     * 获取当前登录用户
     *
     * @return User
     */
    public UserLoginVO getUser() {
        return userInfo.get();
    }

    public UserLoginVO getUserByToken(String token) {
        String s = redisTemplate.opsForValue().get(RedisPrefix.USER_TOKEN + token);
        try {
            return objectMapper.readValue(s, UserLoginVO.class);
        } catch (Exception e) {
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
    }

    public boolean setCurrentUserByToken(String token) {
        UserLoginVO u = getUserByToken(token);
        if (u == null) {
            return false;
        }
        try {
            userInfo.set(u);
        } catch (Exception e) {
            userInfo.remove();
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
        return true;
    }


    public void removeCurrentUser() {
        userInfo.remove();
    }


    /**
     * 获取当前已登录的 admin 管理员 请求的 id, 本项目从 Cookie 中拿到的， sa-token 默认还有别的方式也会拿
     */
    // public User getAdminId() {
    //     return threadLocal.get();
    // }
    //
    // public void setAdminId() {
    //     try {
    //         // threadLocal.set(Long.valueOf((String) StpAdminUtil.getLoginId()));
    //     } catch (Exception e) {
    //         threadLocal.remove();
    //         throw new BusinessException(ResultCodeEnum.AuthError);
    //     }
    // }
    //
    // public void removeAdminId() {
    //     threadLocal.remove();
    // }
}
