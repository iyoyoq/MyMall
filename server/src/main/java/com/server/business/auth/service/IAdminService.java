package com.server.business.auth.service;

import com.server.business.auth.domain.dto.AdminLoginDto;
import com.server.business.auth.domain.vo.LoginVo;

public interface IAdminService {
    LoginVo passwordLogin(AdminLoginDto dto);
}
