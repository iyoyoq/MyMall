package com.server.pojo.dto;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.exception.BusinessException;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/11 18:05
 */
@Data
public class ProductInfoCreateDto {
    // 商品标题
    private String title;
    // 商品描述
    private String intro;
    // 商品图片
    private List<String> image;
    // 商品价格
    private Double price;
    // 商品原价
    private Double originalPrice;
    // 发货方式 0邮寄 1自提
    private Integer postType;
    // 地址代码
    private String adcode;
    // 省
    private String province;
    // 市
    private String city;

    // 将 image 属性转换为 JSON 格式的字符串
    public String getImageAsJson() {
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        try {
            List<String> result = image == null ? Collections.emptyList() : image;
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new BusinessException("图片格式不正确");
        }
    }
}
