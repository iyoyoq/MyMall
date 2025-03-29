package com.server.other.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.server.business.auth.domain.User;
import com.server.business.auth.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/28 12:38
 */
@Slf4j
@SpringBootTest
public class MpTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Test
    @Transactional
    public void t1(){
        User user = new User();
        user.setId(1903702918454829058L);
        user.setPhone(1111L);
        int i = userMapper.updateById(user);
    }

}
