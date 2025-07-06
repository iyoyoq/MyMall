package cn.beforedark.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/7/6 22:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    private Integer code;
    private String msg;
    private T result;

    public static R<String> ok() {
        return new R<>(1, "", "");
    }

    /**
     * 未处理的系统异常 RuntimeException or Exception
     */
    public static R<String> systemError() {
        return new R<>(10001, "未处理的系统异常", "");
    }

    /**
     * 普通业务异常
     */
    public static R<String> businessError(String msg) {
        return new R<>(10002, msg, "");
    }

    /**
     * 身份无效
     */
    public static R<String> authError() {
        return new R<>(10003, "身份无效", "");
    }
}
