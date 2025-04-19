package com.server.business.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.business.auth.domain.Admin;
import com.server.business.auth.domain.dto.AdminLoginDto;
import com.server.business.auth.domain.vo.LoginVo;
import com.server.business.auth.mapper.AdminMapper;
import com.server.business.auth.service.IAdminService;
import com.server.config.redis.RedisPrefix;
import com.server.exception.BusinessException;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/19 9:17
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RequestContext requestContext;

    @Override
    public LoginVo passwordLogin(AdminLoginDto dto) {
        Admin admin = adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUserName, dto.getUserName())
                .eq(Admin::getPassword, dto.getPassword())
        );
        if (admin == null || admin.getId() == null) {
            throw new BusinessException("用户名或密码错误");
        }
        // 登录成功
        // 制作 token
        String token = IdUtil.simpleUUID() + RandomUtil.randomStringLowerWithoutStr(16, "");  // token
        LoginVo vo = new LoginVo(token, admin.getId());
        // 存入 redis
        String k = RedisPrefix.ADMIN_TOKEN + token;
        String v = null;
        try {
            v = objectMapper.writeValueAsString(vo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("20250419093645 用户序列化失败", e);
        }
        // redisTemplate.opsForValue().set(k, v, 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(k, v);
        vo.setToken(token);
        // 返回token
        return vo;
    }
}
