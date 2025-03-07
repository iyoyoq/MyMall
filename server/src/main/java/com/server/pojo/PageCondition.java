package com.server.pojo;

import lombok.Data;

/**
 * @Description: 前端传递过来的分页条件
 * @Author: yjy
 * @Date: 2024/12/21 10:48
 */
@Data
public class PageCondition {
    private Long current;
    private Long size;
}
