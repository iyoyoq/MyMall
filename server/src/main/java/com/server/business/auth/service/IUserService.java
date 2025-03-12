package com.server.business.auth.service;


import com.server.business.auth.domain.User;
import com.server.business.auth.domain.vo.UserLoginVO;
import com.server.pojo.dto.UserLoginDto;

public interface IUserService  {



    void sendLoginCode(String phone);

    UserLoginVO login(UserLoginDto dto);

    User getUserByToken(String token);


    User selectById(Long id);
}
