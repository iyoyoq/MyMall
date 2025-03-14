package com.server.business.auth.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.auth.domain.Address;
import com.server.business.auth.domain.Favorite;
import com.server.business.auth.domain.dto.FavoriteCreateDTO;

/**
 * 用户收藏表(Favorite)表服务接口
 *
 * @author localyjy
 * @since 2025-03-06 16:45:46
 */
public interface IFavoriteService {

    Page<Favorite> selectPage(Integer pageNum, Integer pageSize, Favorite favorite);

    Favorite selectById(Long id);

    int insert(FavoriteCreateDTO dto);

    int removeById(Long id);

    int updateById(Favorite favorite);
}

