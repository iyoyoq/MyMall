package com.server.business.product.service.impl;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.business.product.domain.Product;
import com.server.business.product.domain.ProductSnapshot;
import com.server.business.product.mapper.ProductMapper;
import com.server.business.product.mapper.ProductSnapshotMapper;
import com.server.business.product.service.IProductService;
import com.server.pojo.RPage;
import com.server.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public RPage<Product> selectPage(Integer pageNum, Integer pageSize, Product dto) {

        Page<Product> productPage = productMapper.selectProductPage(new Page<>(pageNum, pageSize), dto);
        RPage<Product> result = new RPage<>(productPage);

        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(Product dto) {
        long snapshotId = IdWorker.getId();
        dto.setCurrentSnapshotId(snapshotId);
        int result = productMapper.insert(dto);
        snapshotMapper.insert(new ProductSnapshot()
                .setId(snapshotId)
                .setProductId(dto.getId())
                .setProductInfoJson(JsonUtil.getJson(dto))
        );
        return result;
    }

    @Override
    public Product getById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public int updateById(Product dto) {
        long snapshotId = IdWorker.getId();
        dto.setCurrentSnapshotId(snapshotId);
        int result = productMapper.updateById(dto);
        snapshotMapper.insert(new ProductSnapshot()
                .setId(snapshotId)
                .setProductId(dto.getId())
                .setProductInfoJson(JsonUtil.getJson(dto))
        );
        return result;
    }
}
