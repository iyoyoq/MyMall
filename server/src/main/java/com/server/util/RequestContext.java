package com.server.util;

import cn.dev33.satoken.stp.StpUtil;
import com.server.exception.BusinessException;
import com.server.model.ResultCodeEnum;

/**
 * @Description: 请求上下文
 * @Author: yjy
 * @Date: 2024/12/8 16:41
 */
public class RequestContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 获取当前已登录的用户请求的 id, 本项目从 Cookie 中拿到的， sa-token 默认还有别的方式也会拿
     *
     * @return id
     */
    public static Long getUserId() {
        return threadLocal.get();
    }

    public static void setUserId() {
        try {
            threadLocal.set(Long.valueOf((String) StpUtil.getLoginId()));
        } catch (Exception e) {
            threadLocal.remove();
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
    }

    public static void removeUserId() {
        threadLocal.remove();
    }



    /**
     * 获取当前已登录的 admin 管理员 请求的 id, 本项目从 Cookie 中拿到的， sa-token 默认还有别的方式也会拿
     */
    public static Long getAdminId() {
        return threadLocal.get();
    }

    public static void setAdminId() {
        try {
            threadLocal.set(Long.valueOf((String) StpAdminUtil.getLoginId()));
        } catch (Exception e) {
            threadLocal.remove();
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
    }

    public static void removeAdminId() {
        threadLocal.remove();
    }
}
