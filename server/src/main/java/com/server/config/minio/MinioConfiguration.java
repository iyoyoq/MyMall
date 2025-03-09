package com.server.config.minio;

import com.server.constant.MinioProp;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/8 22:01
 */
@Configuration
public class MinioConfiguration {
    @Autowired
    private MinioProp minioProp;

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint(minioProp.getEndpoint())
                .credentials(minioProp.getAccesskey(), minioProp.getSecretKey())
                .build();
        return client;
    }

}
