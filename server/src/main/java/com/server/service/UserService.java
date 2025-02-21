package com.server.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.server.entity.User;
import com.server.model.dto.UserLoginDto;

public interface UserService extends IService<User> {

    User getUserInfo(Long userId);

    SaTokenInfo login(UserLoginDto request);

    void sendLoginCode(String phone);
}
