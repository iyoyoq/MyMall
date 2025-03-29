package com.server.business.file.service.service;

import cn.hutool.core.lang.UUID;
import com.server.business.file.mapper.FileMapper;
import com.server.business.file.service.IFileService;
import com.server.exception.BusinessException;
import com.server.pojo.R;
import com.server.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/29 15:32
 */
@Service
@Slf4j
public class FileService implements IFileService {
    @Autowired
    private RequestContext requestContext;

    @Value("${my-mall.file.upload-path}")
    private String uploadPath;

    @Value("${my-mall.file.access-url}")
    private String accessUrl;


    @Autowired
    private FileMapper fileMapper;


    @Override
    public String insert(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }
        try {
            // 生成唯一标识符
            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();

            // 目标文件路径: uploadPath/uuid/原始文件名
            Path dirPath = Paths.get(uploadPath, uuid);
            Files.createDirectories(dirPath); // 确保目录存在

            File destFile = new File(dirPath.toFile(), originalFilename);
            file.transferTo(destFile);

            // 返回文件访问 URL
            String webUrl = accessUrl + "/" + uuid + "/" + originalFilename;
            fileMapper.insert(new com.server.business.file.domain.File()
                    .setOriginName(originalFilename)
                    .setWebUrl(webUrl)
                    .setCreateId(requestContext.getUser().getId()));
            log.info("文件上传成功: {}", webUrl);
            return webUrl;
        } catch (IOException e) {
            throw new BusinessException("文件上传失败");
        }

    }
}
