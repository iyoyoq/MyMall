package com.server.business.file.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description: 文件表
 * @Author: yjy
 * @Date: 2025/3/28 16:51
 */
@Data
@TableName("file_file")
@Accessors(chain = true)
public class File {

    private Long id;

    private Long createId;

    private String originName;

    private String webUrl;

    private LocalDateTime createTime;

}
