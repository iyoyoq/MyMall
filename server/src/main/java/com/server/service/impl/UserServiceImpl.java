package com.server.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.constant.RedisPrefix;
import com.server.entity.User;
import com.server.exception.BusinessException;
import com.server.mapper.UserMapper;
import com.server.model.dto.UserLoginDto;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public User getUserInfo(Long userId) {
        User user = lambdaQuery().eq(User::getId, userId).one();
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    @Override
    public SaTokenInfo login(UserLoginDto request) {
        String userCode = request.getCode();
        String phone = request.getPhone();
        String redis_code = redisTemplate.opsForValue().get(RedisPrefix.phone_msg_code + phone);
        if (StrUtil.isEmpty(redis_code)) throw new BusinessException("未发送验证码");
        if (!redis_code.equals(userCode)) throw new BusinessException("验证码错误");
        // 1.查询用户是否存在
        User user = lambdaQuery().eq(User::getPhone, request.getPhone()).one();
        // 2.不存在 自动创建用户
        if (null == user) {
            user = new User();
            String numbers = RandomUtil.randomNumbers(9);
            user.setNumber(numbers);
            user.setPhone(Long.valueOf(request.getPhone()));
            user.setProvince(request.getProvince());
            user.setCity(request.getCity());
            user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
            user.setNickName("用户" + numbers);
            // user.setCreateTime(new Date().getTime()); 实体类自动填充
            // user.setUpdateTime(new Date().getTime());
            user.setStatus(9);
            boolean save = save(user);
            if (!save) throw new RuntimeException("用户添加失败");
        } else {
            if (user.getStatus() == 0) {
                throw new BusinessException("账户被禁用");
            }
            user.setProvince(request.getProvince());
            user.setCity(request.getCity());
            user.setUpdateTime(new Date().getTime());
            boolean update = updateById(user);
            if (!update) throw new RuntimeException("用户更新失败");
        }
        StpUtil.login(user.getId());
        redisTemplate.delete(RedisPrefix.phone_msg_code + request.getPhone());
        // 返回token
        return StpUtil.getTokenInfo();
    }

    @Override
    public void sendLoginCode(String phone) {
        redisTemplate.opsForValue().set(RedisPrefix.phone_msg_code + phone, "123456", 120, TimeUnit.SECONDS);
    }

}
