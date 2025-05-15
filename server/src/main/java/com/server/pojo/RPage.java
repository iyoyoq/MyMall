package com.server.pojo;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * @Description: 统一 page 响应结果
 * @Author: yjy
 * @Date: 2025/4/1 22:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RPage<T> {
    private List<T> records;
    private Long total;
    public RPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> mpPage) {
        records = mpPage.getRecords();
        total = mpPage.getTotal();
    }

    public static <T>  RPage<T> empty(){
        return new RPage<>(Collections.emptyList(), 0L);
    }
}
