package com.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("m3_comment")
public class Comment {
    @TableId
    private Long id;
    private Long productId;
    private Long parentId;
    private Long pubUserId;
    private String pubNickname;
    private Long parentUserId;
    private String parentUserNickname;
    private String content;
    private Long createTime;
    private Integer status;
}
