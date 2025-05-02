package com.server.business.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Favorite;
import com.server.business.product.domain.dto.FavoriteCreateDto;

/**
 * 用户收藏表(Favorite)表服务接口
 *
 * @author localyjy
 * @since 2025-03-06 16:45:46
 */
public interface IFavoriteService {

    Page<Favorite> selectPage(Integer pageNum, Integer pageSize, Favorite favorite);

    Favorite selectById(Long id);

    int insert(FavoriteCreateDto dto);

    int removeById(Long id);

    int updateById(Favorite favorite);

    int update(Favorite favorite);
}

