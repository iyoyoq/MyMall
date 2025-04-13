package com.server.business.product.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Favorite;
import com.server.business.product.domain.dto.FavoriteCreateDto;
import com.server.business.product.mapper.FavoriteMapper;
import com.server.business.product.service.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户收藏表(Favorite)表服务实现类
 *
 * @author localyjy
 * @since 2025-03-06 16:45:48
 */
@Service("authFavoriteService")
public class FavoriteServiceImpl  implements IFavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public Page<Favorite> selectPage(Integer pageNum, Integer pageSize, Favorite favorite) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>(favorite);
        return favoriteMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public Favorite selectById(Long id) {
        return favoriteMapper.selectById(id);
    }

    @Override
    public int insert(FavoriteCreateDto dto) {
        Favorite db = BeanUtil.copyProperties(dto, Favorite.class);
        return favoriteMapper.insert(db);
    }

    @Override
    public int removeById(Long id) {
        return favoriteMapper.deleteById(id);
    }

    @Override
    public int updateById(Favorite favorite) {
        return favoriteMapper.updateById(favorite);
    }
}

