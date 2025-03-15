package com.server.util;

import com.server.business.auth.domain.User;
import com.server.business.auth.service.IUserService;
import com.server.exception.BusinessException;
import com.server.pojo.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 请求上下文
 * @Author: yjy
 * @Date: 2024/12/8 16:41
 */
@Component
public class RequestContext {

    @Autowired
    private IUserService userService;

    private final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    /**
     * 获取当前登录用户
     * @return User
     */
    public User getUser() {
        return threadLocal.get();
    }

    public boolean setCurrentUserByToken(String token) {
        try {
            User u = userService.getUserByToken(token);
            if (u == null) {
                return false;
            }
            threadLocal.set(u);
        } catch (Exception e) {
            threadLocal.remove();
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
        return true;
    }

    public void removeCurrentUser() {
        threadLocal.remove();
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
