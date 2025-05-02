package com.server.pojo;


import com.server.exception.BusinessException;

import static com.server.pojo.ResultCodeEnum.BusinessError;
/**
 * 统一响应结果
 */
public class R<T> {

    private Integer code;
    private String msg;
    private T result;

    public R(Integer code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }


    public static R<String> ok() {
        return new R<>(ResultCodeEnum.Success.getCode(), "", "");
    }

    public static <T> R<T> ok(T result) {
        return new R<>(ResultCodeEnum.Success.getCode(), "", result);
    }

    public static R<String> error(String msg) {
        return new R<>(BusinessError.getCode(), msg, "");
    }

    public static R<String> judge(boolean success, String errMsg) {
        if(!success){
            throw new BusinessException(errMsg);
        }
        return success ? R.ok() : R.error(errMsg);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
