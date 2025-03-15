package com.server.other;

import com.server.business.auth.domain.User;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/15 10:14
 */
public class OtherTest {

    @Test
    void fun() {
        Function<User, Long> function = new Function<User, Long>() {
            @Override
            public Long apply(User user) {
                return null;
            }
        };
    }

}
