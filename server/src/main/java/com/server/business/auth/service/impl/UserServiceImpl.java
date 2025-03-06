package com.server.business.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.business.auth.mapper.UserMapper;
import com.server.business.auth.domain.vo.UserLoginVo;
import com.server.business.auth.domain.User;
import com.server.business.auth.service.IUserService;
import com.server.config.redis.RedisPrefix;
import com.server.exception.BusinessException;
import com.server.model.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // @Override
    // public User getUserInfo(Long userId) {
    //     User user = lambdaQuery().eq(User::getId, userId).one();
    //     if (user == null) {
    //         throw new BusinessException("用户不存在");
    //     }
    //     return user;
    // }

    @Override
    public void sendLoginCode(String phone) {
        redisTemplate.opsForValue().set(RedisPrefix.phone_msg_code + phone, "123456", 120, TimeUnit.SECONDS);
    }

    @Override
    public UserLoginVo login(UserLoginDto request) {
        String userCode = request.getCode();
        String phone = request.getPhone();
        String redisCode = redisTemplate.opsForValue().get(RedisPrefix.phone_msg_code + phone);
        if (StrUtil.isEmpty(redisCode)) throw new BusinessException("未发送验证码");
        if (!redisCode.equals(userCode)) throw new BusinessException("验证码错误");
        // 1.查询用户是否存在
        User user = lambdaQuery().eq(User::getPhone, request.getPhone()).one();
        // 2.不存在 自动创建用户
        if (null == user) {
            user = new User();
            String numbers = RandomUtil.randomNumbers(9);
            user.setNumber(numbers);
            user.setPhone(Long.valueOf(request.getPhone()));
            user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
            user.setNickName("用户" + numbers);
            // user.setCreateTime(LocalDateTime.now()); //实体类自动填充
            // user.setUpdateTime(LocalDateTime.now());
            user.setStatus(1);
            boolean save = save(user);
            if (!save) throw new BusinessException("用户添加失败");
        } else {
            if (user.getStatus() == 0) {
                throw new BusinessException("账户被禁用");
            }
            user.setUpdateTime(LocalDateTime.now());
            boolean update = updateById(user);
            if (!update) throw new BusinessException("用户更新失败");
        }
        // 制作 token
        String token = RandomUtil.randomString(45);
        String key = RedisPrefix.user_token + user.getId();   // 即使是新用户，mp也会得到userId
        redisTemplate.opsForValue().set(key, token, 24, TimeUnit.HOURS);
        redisTemplate.delete(RedisPrefix.phone_msg_code + request.getPhone());
        UserLoginVo vo = BeanUtil.copyProperties(user, UserLoginVo.class);
        vo.setToken(token);
        // 返回token
        return vo;
    }


}
