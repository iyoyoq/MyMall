package com.server.business.product.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.product.domain.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户收藏表(Favorite)表数据库访问层
 *
 * @author localyjy
 * @since 2025-03-06 16:45:49
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {


    /**
     * 用户是否收藏
     */
    default boolean userIsFavorite(Long userId, Long productId) {
        List<Favorite> favorites = selectList(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getProductId, productId)
        );
        return favorites != null && !favorites.isEmpty();
    }


    List<Favorite> selectPageForSelf(@Param("favorite") Favorite favorite,
                                     @Param("limit") Integer limit,
                                     @Param("offset") Integer offset);

    /**
     * 用户收藏数
     */
    Long countForSelf(Long userId);

    default int batchCancel(List<Long> favoriteIdList, Long userId) {
        return delete(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .in(Favorite::getId, favoriteIdList)
        );
    }
}

