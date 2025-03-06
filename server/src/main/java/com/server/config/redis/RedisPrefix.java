package com.server.config.redis;

public interface RedisPrefix {

    // 用户登录 短信验证码
    String phone_msg_code = "auth:phone_msg_code:";
    // 用户 token
    String user_token = "auth:user_token:";

}
