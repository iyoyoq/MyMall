package com.server.config.redis;

public interface RedisPrefix {

    // 用户登录 短信验证码
    String PHONE_MSG_CODE = "auth:phone_msg_code:";
    // 用户 token
    String USER_TOKEN = "auth:user_token:";

}
