package cn.beforedark.serviceauth.service;

import cn.beforedark.common.model.auth.dto.UserLoginDTO;
import cn.beforedark.common.model.auth.vo.LoginVO;

public interface IUserService {
    LoginVO login(UserLoginDTO dto);

    void sendLoginCode(String phone);
}
