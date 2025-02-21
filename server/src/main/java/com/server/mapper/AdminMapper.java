package com.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台管理员表(Admin)表数据库访问层
 *
 * @author localyjy
 * @since 2024-12-18 23:53:38
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}

