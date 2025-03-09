package com.server.config.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.exception.BusinessException;
import com.server.pojo.R;
import com.server.pojo.ResultCodeEnum;
import com.server.util.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * @Description: 用户端 登录校验拦截器
 * @Author: yjy
 * @Date: 2024/12/8 20:08
 */
@Component
@Slf4j
public class UserCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestContext requestContext;

    /**
     * 本项目从 headers.Authorization = `Bearer ${token}` 中拿到的凭证
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Cookie 认证的项目，浏览器预检请求OPTIONS不会携带 Cookie, 先放行
        // if (request.getMethod().equals("OPTIONS")) {
        //     return true;
        // }
        boolean ok = false;
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            // 提取 Bearer 后面的内容
            String token = authorization.substring("Bearer ".length()).trim();
            ok = requestContext.setCurrentUserByToken(token);
        }
        if (!ok) {
            throw new BusinessException(ResultCodeEnum.AuthError);
        }
        // 通过验证
        return ok;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 如果不删除ThreadLocal中的信息,会有内存泄露的风险
        requestContext.removeCurrentUser();
    }
}
