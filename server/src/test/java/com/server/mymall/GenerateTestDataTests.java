package com.server.mymall;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.server.business.product.domain.Product;
import com.server.business.product.domain.ProductCategory;
import com.server.business.product.domain.dto.ProductSkuDto;
import com.server.business.product.mapper.ProductCategoryMapper;
import com.server.business.product.mapper.ProductMapper;
import com.server.business.product.service.IProductService;
import com.server.business.product.service.IProductSkuService;
import com.server.util.RandomStringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

/**
 * @Description: 生成测试数据
 * @Author: yjy
 * @Date: 2025/5/5 19:32
 */
@SpringBootTest
public class GenerateTestDataTests {

    @Autowired
    IProductSkuService productSkuService;

    @Autowired
    IProductService productService;

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Autowired
    ProductMapper productMapper;


    /*
    上传接口载荷示例
    {
        "id": null,
        "name": "商品20250505224336",
        "categoryId": "6",
        "description": "商品描述信息20250505224426",
        "mainImage": "http://localhost:45001/mymall_files/dd480ed3-87b2-4032-ab78-fd2d0770db24/20250505224143.png",
        "images": "http://localhost:45001/mymall_files/47136dcb-331d-4dc3-8db4-9cef3f0db247/20250505224221.png,http://localhost:45001/mymall_files/1816a796-6775-4a26-a007-dbf24503ebb3/20250505224221.png,http://localhost:45001/mymall_files/367e71d2-2a89-49d3-b75a-9013cc16a769/20250505224221.png",
        "shippingFee": 0,
    }

    */
    // @Test
    void saveProduct() {
        // 查询所有非顶层分类（parent_id != -1）
        List<Long> categoryIds = productCategoryMapper.selectList(
                        new LambdaQueryWrapper<ProductCategory>().ne(ProductCategory::getParentId, -1)
                ).stream()
                .map(ProductCategory::getId)
                .toList();
        for (int i = 0; i < 280; i++) {
            // 随机选一个分类id
            Long randomCategoryId = categoryIds.get(new Random().nextInt(categoryIds.size()));

            Product product = new Product()
                    .setName("商品" + RandomStringUtil.randomString(8))
                    .setCategoryId(randomCategoryId)
                    .setDescription("商品描述信息" + RandomStringUtil.randomString(8))
                    .setMainImage("http://localhost:45001/mymall_files/dd480ed3-87b2-4032-ab78-fd2d0770db24/20250505224143.png")
                    .setImages("http://localhost:45001/mymall_files/47136dcb-331d-4dc3-8db4-9cef3f0db247/20250505224221.png,http://localhost:45001/mymall_files/1816a796-6775-4a26-a007-dbf24503ebb3/20250505224221.png,http://localhost:45001/mymall_files/367e71d2-2a89-49d3-b75a-9013cc16a769/20250505224221.png");
            productService.insert(product);
        }

    }


    /*
    上传接口载荷示例
    {
        "productId": "1919402831154925569",
        "skuAttrNameList": [
            "颜色",
            "规格"
        ],
        "skuList": [
            {
                "颜色": "黑",
                "规格": "中",
                "price": 2000,
                "priceYuan": 20,
                "stockQuantity": 5000,
                "skuAttrValueList": [
                    "黑",
                    "中"
                ]
            },
            {
                "颜色": "黑",
                "规格": "大",
                "price": 3000,
                "priceYuan": 30,
                "stockQuantity": 5000,
                "skuAttrValueList": [
                    "黑",
                    "大"
                ]
            },
            {
                "颜色": "白",
                "规格": "中",
                "price": 2000,
                "priceYuan": 20,
                "stockQuantity": 5000,
                "skuAttrValueList": [
                    "白",
                    "中"
                ]
            },
            {
                "颜色": "白",
                "规格": "大",
                "price": 3000,
                "priceYuan": 30,
                "stockQuantity": 5000,
                "skuAttrValueList": [
                    "白",
                    "大"
                ]
            },
            {
                "颜色": "灰",
                "规格": "中",
                "price": 2000,
                "priceYuan": 20,
                "stockQuantity": 5000,
                "skuAttrValueList": [
                    "灰",
                    "中"
                ]
            },
            {
                "颜色": "灰",
                "规格": "大",
                "price": 3000,
                "priceYuan": 30,
                "stockQuantity": 5000,
                "skuAttrValueList": [
                    "灰",
                    "大"
                ]
            }
        ]
    }


     */
    // @Test
    void saveProductSku() {

        // 所有没有SKU属性的商品
        List<Product> products = productMapper.selectList(new LambdaQueryWrapper<Product>()
                .isNull(Product::getSkuAttrNames)
        );

        // 给他批量加上SKU属性
        for (int i = 0; i < products.size(); i++) {
            ProductSkuDto dto = new ProductSkuDto();

            dto.setProductId(products.get(i).getId());

            dto.setSkuAttrNameList(List.of("颜色", "规格"));

            dto.setSkuList(List.of(
                    // 黑-中
                    new ProductSkuDto.SingleSku(2000, 5000, List.of("黑", "中")),
                    new ProductSkuDto.SingleSku(3000, 5000, List.of("黑", "大")),
                    new ProductSkuDto.SingleSku(2000, 5000, List.of("白", "中")),
                    new ProductSkuDto.SingleSku(3000, 5000, List.of("白", "大")),
                    new ProductSkuDto.SingleSku(2000, 5000, List.of("灰", "中")),
                    new ProductSkuDto.SingleSku(3000, 5000, List.of("灰", "大"))
            ));
            productSkuService.insertOrUpdate(dto);
        }
    }

}

