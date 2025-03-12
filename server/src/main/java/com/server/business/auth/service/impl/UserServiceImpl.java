package com.server.business.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.business.auth.domain.vo.UserLoginVO;
import com.server.business.auth.domain.User;
import com.server.business.auth.mapper.UserMapper;
import com.server.business.auth.service.IUserService;
import com.server.config.redis.RedisPrefix;
import com.server.exception.BusinessException;
import com.server.pojo.dto.UserLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public void sendLoginCode(String phone) {
        redisTemplate.opsForValue().set(RedisPrefix.PHONE_MSG_CODE + phone, "123456", 120, TimeUnit.SECONDS);
    }

    @Override
    public UserLoginVO login(UserLoginDto request) {
        String userCode = request.getCode();
        String phone = request.getPhone();
        String redisCode = redisTemplate.opsForValue().get(RedisPrefix.PHONE_MSG_CODE + phone);
        if (StrUtil.isEmpty(redisCode)) throw new BusinessException("未发送验证码");
        if (!redisCode.equals(userCode)) throw new BusinessException("验证码错误");
        // 1.查询用户是否存在
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
        // 2.不存在 自动创建用户
        if (null == user) {
            user = new User();
            String numbers = RandomUtil.randomNumbers(9);
            user.setCode(numbers);
            user.setPhone(Long.valueOf(request.getPhone()));
            user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
            user.setNickName("用户" + numbers);
            // user.setCreateTime(LocalDateTime.now()); //实体类自动填充
            // user.setUpdateTime(LocalDateTime.now());
            user.setStatus(1);
            int save = userMapper.insert(user);
            if (save < 1) throw new BusinessException("用户添加失败");
        } else {
            if (user.getStatus() == 0) {
                throw new BusinessException("账户被禁用");
            }
            user.setUpdateTime(LocalDateTime.now());
            int update = userMapper.updateById(user);
            if (update < 1) throw new BusinessException("用户更新失败");
        }
        // 制作 token
        String token = IdUtil.simpleUUID();  // token
        String k = RedisPrefix.USER_TOKEN + token;   // 即使是新用户，mp也会得到userId
        String v = null;
        try {
            v = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            log.error("用户序列化失败", e);
            throw new RuntimeException(e);
        }
        redisTemplate.opsForValue().set(k, v, 24, TimeUnit.HOURS);
        redisTemplate.delete(RedisPrefix.PHONE_MSG_CODE + request.getPhone());
        UserLoginVO vo = BeanUtil.copyProperties(user, UserLoginVO.class);
        vo.setToken(token);
        // 返回token
        return vo;
    }

    @Override
    public User getUserByToken(String token) {
        String s = redisTemplate.opsForValue().get(RedisPrefix.USER_TOKEN + token);
        try {
            return objectMapper.readValue(s, User.class);
        } catch (JsonProcessingException e) {
            log.error("用户反序列化失败", e);
        }
        return null;
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }


}
