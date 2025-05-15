package com.server.business.product.service;


import com.server.business.product.domain.Favorite;
import com.server.pojo.RPage;

import java.util.List;

/**
 * 用户收藏表(Favorite)表服务接口
 *
 * @author localyjy
 * @since 2025-03-06 16:45:46
 */
public interface IFavoriteService {

    RPage<Favorite> pageForSelf(Integer pageNum, Integer pageSize, Favorite favorite);

    int batchCancel(List<Long> favoriteIdList);

    int add(Favorite favorite);

    int cancel(Long productId);
}

