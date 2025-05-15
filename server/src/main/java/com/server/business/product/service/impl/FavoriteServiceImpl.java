package com.server.business.product.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.server.business.product.domain.Favorite;
import com.server.business.product.mapper.FavoriteMapper;
import com.server.business.product.service.IFavoriteService;
import com.server.pojo.RPage;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户收藏表(Favorite)表服务实现类
 *
 * @author localyjy
 * @since 2025-03-06 16:45:48
 */
@Service("authFavoriteService")
public class FavoriteServiceImpl implements IFavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private RequestContext requestContext;

    @Override
    public RPage<Favorite> pageForSelf(Integer pageNum, Integer pageSize, Favorite favorite) {
        favorite.setUserId(requestContext.getUser().getId());
        Long count = favoriteMapper.countForSelf(favorite.getUserId());
        if (count.equals(0L)) {
            return RPage.empty();
        }

        List<Favorite> list = favoriteMapper.selectPageForSelf(favorite, pageSize, (pageNum-1) * pageSize );

        return new RPage<>(list, count);
    }





    @Override
    public int batchCancel(List<Long> favoriteIdList) {
       return favoriteMapper.batchCancel(favoriteIdList, requestContext.getUser().getId());
    }

    @Override
    public int add(Favorite favorite) {
        favorite.setUserId(requestContext.getUser().getId());
        return favoriteMapper.insert(favorite);
    }

    @Override
    public int cancel(Long productId) {
        return favoriteMapper.delete(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getProductId, productId)
                .eq(Favorite::getUserId, requestContext.getUser().getId())
        );
    }


}

