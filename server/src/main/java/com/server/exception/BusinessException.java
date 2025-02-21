package com.server.exception;


import com.server.model.ResultCodeEnum;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private final ResultCodeEnum codeEnum;

    // 这边的异常信息 msg 会返回给前端用户
    public BusinessException(String msg) {
        super(msg);
        codeEnum = ResultCodeEnum.BusinessError;
    }

    public BusinessException(ResultCodeEnum r) {
        super(String.format("请求错误码：%d, 枚举类名：%s", r.getCode(), r.name()));
        codeEnum = r;
    }

    public Integer getCode() {
        return codeEnum.getCode();
    }


}
