package com.server.business.file.controller;

import com.server.business.file.service.IFileService;
import com.server.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 文件上传使用该接口 访问就访问 nginx服务器上的
 * @Author: yjy
 * @Date: 2025/3/28 15:11
 */
@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private IFileService fileService;

    /**
     * 'multipart/form-data' 格式上传文件
     *
     * @param file file
     * @return webUrl
     */
    @PostMapping("/upload")
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        String webUrl = fileService.insert(file);
        return R.ok(webUrl);
    }
}

