package com.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.entity.Admin;
import com.server.mapper.AdminMapper;
import com.server.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * 后台管理员表(Admin)表服务实现类
 *
 * @author localyjy
 * @since 2024-12-18 23:53:39
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}

