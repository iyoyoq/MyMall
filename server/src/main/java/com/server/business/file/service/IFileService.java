package com.server.business.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/29 15:31
 */
public interface IFileService {
    /**
     * 上传文件
     * @param file file
     * @return webUrl
     */
    String insert(MultipartFile file);
}
