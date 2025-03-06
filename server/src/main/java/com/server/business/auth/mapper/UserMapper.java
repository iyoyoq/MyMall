package com.server.business.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.auth.domain.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
}
