package com.server;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/7 21:38
 */
public class SimpleTests {
    @Test
    void uuid(){
        String simpleUUID = IdUtil.simpleUUID();
        System.out.println(simpleUUID);
    }
}
