package com.server.business.product.service.impl;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Product;
import com.server.business.product.domain.ProductSnapshot;
import com.server.business.product.mapper.FavoriteMapper;
import com.server.business.product.mapper.ProductCategoryMapper;
import com.server.business.product.mapper.ProductMapper;
import com.server.business.product.mapper.ProductSnapshotMapper;
import com.server.business.product.service.IProductService;
import com.server.business.product.service.IProductSkuService;
import com.server.pojo.RPage;
import com.server.util.JsonUtil;
import com.server.util.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 20:37
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSnapshotMapper snapshotMapper;
    @Autowired
    private IProductSkuService skuService;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private ProductCategoryMapper categoryMapper;

    @Override
    public RPage<Product> selectPage(Integer pageNum, Integer pageSize, Product dto) {

        Set<Long> allSonCategoryId = categoryMapper.getAllSonByParentId(dto.getCategoryId());

        Page<Product> productPage = productMapper.selectProductPage(new Page<>(pageNum, pageSize), dto, allSonCategoryId);
        RPage<Product> result = new RPage<>(productPage);

        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(Product dto) {
        int result = productMapper.insert(dto);
        generateProductSnapshot(dto.getId());
        return result;
    }

    @Override
    public Product getById(Long id) {
        Product vo = productMapper.selectById(id);
        vo.setSku(skuService.getByProductId(id, List.of(1)));
        if (requestContext.isUser()) {
            vo.setUserIsFavorite(favoriteMapper.userIsFavorite(requestContext.getUser().getId(), id));
        }
        return vo;
    }

    @Override
    public int updateById(Product dto) {
        int result = productMapper.updateById(dto);
        generateProductSnapshot(dto.getId());
        return result;
    }

    @Override
    public int logicDelete(Long productId) {
        return productMapper.updateById(new Product().setId(productId).setStatus(-1));
    }

    private void generateProductSnapshot(Long productId) {
        Product product = productMapper.selectById(productId);
        Long snapshotId = IdWorker.getId();
        snapshotMapper.insert(new ProductSnapshot()
                .setId(snapshotId)
                .setProductId(productId)
                .setProductInfoJson(JsonUtil.getJson(product))
        );
        productMapper.updateById(new Product().setId(productId).setCurrentSnapshotId(snapshotId));
    }
}
