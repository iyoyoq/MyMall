package com.server.pojo.dto;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.server.entity.ProductInfo;
import com.server.pojo.PageCondition;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @Description: 分页查询条件
 * @Author: yjy
 * @Date: 2024/12/21 10:11
 */
@Data
public class ProductInfoQueryConditionDto extends PageCondition {

    // 商品标识
    private Long id;
    // 发布用户id
    private Long userId;
    // 商品标题
    private String title;
    // 状态 1 待审核 2 审核失败 9 上线 12 卖出
    private Integer status;
    // 创建时间 左
    private Long createTimeStart;
    // 创建时间 右
    private Long createTimeEnd;

    // 构造 QueryWrapper 对象
    public QueryWrapper<ProductInfo> toQueryWrapper() {
        QueryWrapper<ProductInfo> wrapper = new QueryWrapper<>();


        // 动态构建查询条件
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (StringUtils.hasText(title)) {
            wrapper.like("title", title);
        }
        if (status != null) {
            wrapper.eq("status", status);
        } else {
            // 逻辑删除 status 不为 0
            wrapper.ne("status", 0);
        }
        if (createTimeStart != null) {
            wrapper.ge("create_time", createTimeStart);
        }
        if (createTimeEnd != null) {
            wrapper.le("create_time", createTimeEnd);
        }
        wrapper.orderByDesc("create_time");
        return wrapper;
    }


    // 商品描述
    // private String intro;
    // 商品图片
    // private String image;
    // 商品价格
    // private Long price;
    // 商品原价
    // private Long originalPrice;
    // 发货方式 0邮寄 1自提
    // private Integer postType;
    // 想要的人数
    // private Integer likeCount;
    // 地址代码
    // private String adcode;
    // 省
    // private String province;
    // 市
    // private String city;
    // 区
    // private String district;
    // 创建时间
    // private Long createTime;
    // 更新时间
    // private Long updateTime;

}
