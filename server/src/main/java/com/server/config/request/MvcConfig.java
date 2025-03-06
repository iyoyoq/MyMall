package com.server.config.request;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 请求处理
 * @Author: yjy
 * @Date: 2024/12/7 18:14
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // .allowedOrigins("http://localhost:5173,http://localhost:5174")
                .allowedOrigins("*") // 报错 allowCredentials 冲突
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                // .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(new UserCheckInterceptor())
        //         .addPathPatterns("/**")
        //         .excludePathPatterns("/auth/**")
        //         .excludePathPatterns("/admin/**")
        //         .excludePathPatterns("/upload/**");
        //
        // registry.addInterceptor(new AdminCheckInterceptor())
        //         .addPathPatterns("/admin/**")
        //         .excludePathPatterns("/admin/auth/**");
    }

}
