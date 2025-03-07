package com.server.pojo;

/**
 * 需要前端处理的写在这，否则统一用 SystemError(10001), //系统异常
 */
public enum ResultCodeEnum {

    Success(1),
    SystemError(10001), //未处理的系统异常 RuntimeException or Exception
    BusinessError(10002), // 普通业务异常
    AuthError(10003)  // 身份无效
    ;


    private Integer code;

    ResultCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
