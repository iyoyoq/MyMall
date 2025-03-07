package com.server.business.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.auth.domain.Favorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户收藏表(Favorite)表数据库访问层
 *
 * @author localyjy
 * @since 2025-03-06 16:45:49
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

}

