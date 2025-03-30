package com.server.config.mybatisplus;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2024/12/21 10:23
 */
@Configuration
// @MapperScan("com.server.business")
public class MyBatisPlusConfig {

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 如果配置多个插件, 切记分页最后添加
        PaginationInnerInterceptor config = new PaginationInnerInterceptor(DbType.MYSQL);
        // 单页分页条数限制
        config.setMaxLimit(100L);
        // 如果想临时不分页，可以在初始化 IPage 时 size 参数传入小于 0 的值
        interceptor.addInnerInterceptor(config);
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }
}
