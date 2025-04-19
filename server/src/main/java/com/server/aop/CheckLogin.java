package com.server.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckLogin {
    /**
     * 拥有任意一个角色即可访问
     * 如果就这么用 @CheckLogin 意思是至少登录，游客不行
     */
    LoginType[] allowRole() default {LoginType.USER, LoginType.ADMIN};

}
