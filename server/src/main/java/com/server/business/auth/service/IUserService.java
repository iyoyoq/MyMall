package com.server.business.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.server.business.auth.domain.User;
import com.server.business.auth.domain.vo.UserLoginVo;
import com.server.model.dto.UserLoginDto;

public interface IUserService extends IService<User> {



    void sendLoginCode(String phone);

    UserLoginVo login(UserLoginDto dto);
}
