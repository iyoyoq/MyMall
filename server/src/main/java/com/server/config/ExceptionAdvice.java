package com.server.config;


import com.server.exception.BusinessException;
import com.server.pojo.R;
import com.server.pojo.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public R<String> handleBusinessException(BusinessException e) {
        String msg = "业务异常：" + e.getMessage();
        log.error(msg, e);
        return new R<>(e.getCode(), e.getMessage(), "");
    }

    @ExceptionHandler(Exception.class)
    public R<String> other(Exception e) {
        String msg = "未处理的系统异常：" + e.getMessage();
        log.error(msg, e);
        return new R<>(ResultCodeEnum.SystemError.getCode(), "系统异常","");
    }

}
