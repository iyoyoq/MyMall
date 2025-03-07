package com.server.business.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.server.business.auth.domain.User;
import com.server.business.auth.domain.vo.UserLoginVO;
import com.server.pojo.dto.UserLoginDto;

public interface IUserService extends IService<User> {



    void sendLoginCode(String phone);

    UserLoginVO login(UserLoginDto dto);
}
