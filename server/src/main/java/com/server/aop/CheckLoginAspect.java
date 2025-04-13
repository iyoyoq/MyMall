package com.server.aop;

import com.server.exception.BusinessException;
import com.server.pojo.ResultCodeEnum;
import com.server.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 登录校验切面
 * @Author: yjy
 * @Date: 2025/4/8 23:23
 */
@Aspect
@Component
@Slf4j
public class CheckLoginAspect {

    @Autowired
    private RequestContext requestContext;

    @Around("@annotation(checkLogin)")
    public Object around(ProceedingJoinPoint joinPoint, CheckLogin checkLogin) throws Throwable {
        log.info("进入切面: 方法 = {}", joinPoint.getSignature());
        LoginType[] roleArr = checkLogin.allowRole();
        for (LoginType role : roleArr) {
            if (LoginType.USER.equals(role)) {
                // 用户登录校验
                requestContext.userLoginCheck();
            } else if (LoginType.ADMIN.equals(role)) {
                // 管理员登录校验
                requestContext.adminLoginCheck();
            } else {
                log.error("20250413160915 不合规的LoginType");
                throw new BusinessException(ResultCodeEnum.AuthError);
            }
        }
        return joinPoint.proceed();
    }


}
