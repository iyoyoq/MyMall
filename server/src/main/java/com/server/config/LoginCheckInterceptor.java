package com.server.config;

import com.server.util.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * @Description: 用户端 登录校验拦截器
 * @Author: yjy
 * @Date: 2024/12/8 20:08
 */
public class LoginCheckInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            // 本项目使用 Cookie 认证，浏览器预检请求OPTIONS不会携带 Cookie, 先放行
            return true;
        }
        RequestContext.setUserId();

        // 通过验证
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 如果不删除ThreadLocal中的信息,会有内存泄露的风险
        RequestContext.removeUserId();
    }
}
