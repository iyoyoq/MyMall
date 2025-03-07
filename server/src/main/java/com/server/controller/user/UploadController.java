package com.server.controller.user;

import cn.hutool.core.util.StrUtil;
import com.server.constant.MinioProp;
import com.server.exception.BusinessException;
import com.server.pojo.R;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/8 21:49
 */
@RestController
@RequestMapping("/upload")
public class UploadController {


    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProp minioProp;

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    /**
     * minio上传图片
     */
    @PostMapping("/image")
    public R uploadImage(@RequestParam("file") MultipartFile image) {
        try {
            String fileName = UUID.randomUUID() + "." + StrUtil.subAfter(image.getOriginalFilename(), ".", true);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String path = formatter.format(new Date()).replace('-', '/');
            // 上传
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProp.getBucket())
                    .object(path + '/' + fileName)
                    .contentType(image.getContentType())
                    .stream(image.getInputStream(), image.getInputStream().available(), -1)
                    .build());
            // 返回结果
            log.info("文件上传成功，{}", fileName);
            HashMap<String, String> result = new HashMap<>();
            result.put("name", fileName);
            result.put("url", minioProp.getEndpoint() + '/' + minioProp.getBucket() + '/' + path + '/' + fileName);
            return R.ok(result);
        } catch (Exception e) {
            log.error(e.toString());
            throw new BusinessException("图片上传失败");
        }
    }
}
