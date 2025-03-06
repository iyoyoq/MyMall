package com.server.business.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.business.auth.domain.Favorite;
import com.server.business.auth.mapper.FavoriteMapper;
import com.server.business.auth.service.IFavoriteService;
import org.springframework.stereotype.Service;

/**
 * 用户收藏表(Favorite)表服务实现类
 *
 * @author localyjy
 * @since 2025-03-06 16:45:48
 */
@Service("authFavoriteService")
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {

}

