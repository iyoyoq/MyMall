package com.server.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: MyBatisPlus 自动填充：https://baomidou.com/guides/auto-fill-field/#%E5%8E%9F%E7%90%86%E6%A6%82%E8%BF%B0
 * @Author: yjy
 * @Date: 2024/12/11 17:23
 */
@Component
public class MyBatisPlusAutoFillHandler implements MetaObjectHandler {

    private static final Logger log = LoggerFactory.getLogger(MyBatisPlusAutoFillHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        // log.info("开始插入填充...");
        this.strictInsertFill(metaObject, "createTime", Long.class, new Date().getTime());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // log.info("开始更新填充...");
        this.strictUpdateFill(metaObject, "updateTime", Long.class, new Date().getTime());
    }
}
