package com.server.business.auth.service;


import com.server.business.auth.domain.User;
import com.server.business.auth.domain.dto.UserLoginDto;
import com.server.business.auth.domain.vo.UserLoginVO;

public interface IUserService  {

    void sendLoginCode(String phone);

    UserLoginVO login(UserLoginDto dto);

    User getUserByToken(String token);


    User selectById(Long id);

    /**
     * 修改自己个人资料
     * @param user user
     * @return 影响行数
     */
    int updateSelfById(User user);
}
