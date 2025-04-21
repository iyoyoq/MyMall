package com.server.business.product.service.impl;

import com.server.business.product.domain.Product;
import com.server.business.product.domain.ProductSku;
import com.server.business.product.domain.dto.ProductSkuDto;
import com.server.business.product.domain.vo.ProductSkuVo;
import com.server.business.product.mapper.ProductMapper;
import com.server.business.product.mapper.ProductSkuMapper;
import com.server.business.product.service.IProductSkuService;
import com.server.exception.BusinessException;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/21 14:11
 */
@Service
public class ProductSkuServiceImpl implements IProductSkuService {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Override
    public ProductSkuVo getByProductId(Long productId) {
        return null;
    }

    @Override
    public int insert(ProductSkuDto dto) {

        if(!dto.check()) throw new BusinessException("20250421152128 请求参数有误");

        List<ProductSkuDto.SingleSku> skuList = dto.getSkuList();

        List<ProductSku> insertList = new ArrayList<>();
        for (ProductSkuDto.SingleSku sku : skuList) {
            sku.setSkuAttrValues(String.join(",",sku.getSkuAttrValueList()));
            sku.setProductId(dto.getProductId());
            sku.setStatus(1);
            insertList.add(sku);
        }

        // 存入 skuAttrNames
        String skuAttrNames = String.join(",", dto.getSkuAttrNameList());
        productMapper.updateById(new Product().setId(dto.getProductId()).setSkuAttrNames(skuAttrNames));

        productSkuMapper.insert(insertList);
        return insertList.size();
    }

    @Override
    public int updateById(ProductSkuDto dto) {
        return 0;
    }
}
