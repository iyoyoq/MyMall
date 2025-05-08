package com.server.business.product.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.product.domain.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/4/6 20:39
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    default Set<Long> getAllSonByParentId(Long parentId) {
        Set<Long> result = new HashSet<>();
        Queue<Long> queue = new LinkedList<>();
        queue.add(parentId);

        while (!queue.isEmpty()) {
            Long currentId = queue.poll();

            List<ProductCategory> sonList = selectList(new LambdaQueryWrapper<ProductCategory>()
                    .select(ProductCategory::getId)
                    .eq(ProductCategory::getParentId, currentId)
                    .eq(ProductCategory::getStatus, 1)
            );

            for (ProductCategory son : sonList) {
                Long sonId = son.getId();
                if (result.add(sonId)) { // 避免重复
                    queue.add(sonId);    // 继续查找下一级
                }
            }
        }

        return result;
    }


}
