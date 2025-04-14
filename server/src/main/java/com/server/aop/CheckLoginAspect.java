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

import java.util.ArrayList;
import java.util.List;

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
        // log.info("进入切面: 方法 = {}", joinPoint.getSignature());
        LoginType[] roleArr = checkLogin.allowRole();

        // 不让空，空就是 Admin
        if (roleArr.length == 0) {
            roleArr = new LoginType[]{LoginType.ADMIN};
        }

        boolean hasValidRole = false;
        List<Exception> exceptionList = new ArrayList<>();

        // 尝试每个允许的角色，只要有一个通过即可
        for (LoginType role : roleArr) {
            if (LoginType.USER.equals(role)) {
                // 用户登录校验
                try {
                    requestContext.userLoginCheck();
                    hasValidRole = true;
                    break;
                } catch (Exception e) {
                    exceptionList.add(e);
                }
            } else if (LoginType.ADMIN.equals(role)) {
                // 管理员登录校验
                try {
                    requestContext.adminLoginCheck();
                    hasValidRole = true;
                    break;
                } catch (Exception e) {
                    exceptionList.add(e);
                }
            } else {
                throw new IllegalArgumentException("20250414193059 不合法的 LoginType");
            }

        }

        if (!hasValidRole) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringBuilder.append("20250414194136 身份校验失败\n"));
            exceptionList.forEach(e -> stringBuilder.append(e.getMessage()).append("\n"));
            log.error(stringBuilder.toString());
            throw new BusinessException(ResultCodeEnum.AuthError);
        }

        return joinPoint.proceed();
    }
}
