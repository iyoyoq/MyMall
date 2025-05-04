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
                .eq(Favorite::getStatus, 1)
        );
        return favorites != null && !favorites.isEmpty();
    }

    /**
     * 更新收藏
     */
    default int update(Favorite favorite) {
        Favorite favoriteInDb = selectOne(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, favorite.getUserId())
                .eq(Favorite::getProductId, favorite.getProductId()));
        if (favoriteInDb == null || favoriteInDb.getId() == null) {
            insert(favorite);
            return 1;
        }

        return updateById(new Favorite().setId(favoriteInDb.getId())
                .setStatus(favorite.getStatus())
        );

    }

    List<Favorite> selectPageForSelf(@Param("favorite") Favorite favorite,
                                        @Param("limit") Integer limit,
                                        @Param("offset") Integer offset);

    /**
     * 用户收藏数
     */
    Long countForSelf(Long userId);

    default int batchCancel(List<Long> favoriteIdList, Long userId){
        return update(new Favorite().setStatus(0),
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
                        .in(Favorite::getId, favoriteIdList)
        );
    }
}

