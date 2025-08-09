package cn.beforedark.serviceauth.service.impl;

import cn.beforedark.common.model.auth.dto.UserLoginDTO;
import cn.beforedark.common.model.auth.vo.LoginVO;
import cn.beforedark.serviceauth.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: yjy
 * @Date: 2025/8/7 23:27
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public LoginVO login(UserLoginDTO dto) {
        return null;
    }

    @Override
    public void sendLoginCode(String phone) {

    }
}
