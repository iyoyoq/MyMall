package com.server.business.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.auth.domain.Address;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户地址表(Address)表数据库访问层
 *
 * @author localyjy
 * @since 2025-03-06 16:36:50
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

}

