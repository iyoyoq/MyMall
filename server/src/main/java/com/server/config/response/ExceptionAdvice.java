package com.server.config.response;


import com.server.exception.BusinessException;
import com.server.pojo.R;
import com.server.pojo.ResultCodeEnum;
import com.server.util.RequestContext;
import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

@RestControllerAdvice
public class ExceptionAdvice {


    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public R<String> handleBusinessException(BusinessException e) {
        String msg = "业务异常：" + e.getMessage() + " " + simpleInfo();
        log.error(msg, e);
        return new R<>(e.getCode(), e.getMessage(), "");
    }

    @ExceptionHandler(Exception.class)
    public R<String> other(Exception e) {
        String msg = "未处理的系统异常：" + e.getMessage() + " " + simpleInfo();
        log.error(msg, e);
        return new R<>(ResultCodeEnum.SystemError.getCode(), "系统异常", "");
    }


    public static String simpleInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String result = "";
        result += request.getMethod() + request.getRequestURI();
        return result;
    }

    public static void logRequestDetails() throws IOException {
        // 获取当前请求的ServletRequestAttributes
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 打印请求的基本信息
        System.out.println("-----------------请求基本信息-------------------------");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Protocol: " + request.getProtocol());
        System.out.println("Remote Address: " + request.getRemoteAddr());
        System.out.println("------------------------------------------");
        // 打印请求头
        System.out.println("-------------------请求头-----------------------");
        System.out.println("Request Headers:");
        System.out.println("------------------------------------------");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        // 打印请求参数
        System.out.println("-------------------请求参数-----------------------");
        System.out.println("Request Parameters:");
        System.out.println("------------------------------------------");
        request.getParameterMap().forEach((key, value) -> {
            System.out.println(key + ": " + String.join(",", value));
        });

        // 打印请求体内容
        String requestBody = getRequestBody(request);
        System.out.println("-------------------请求体-----------------------");
        System.out.println("Request Body: ");
        System.out.println("------------------------------------------");
        System.out.println(requestBody);
    }

    private static String getRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        return requestBody.toString();
    }
}
