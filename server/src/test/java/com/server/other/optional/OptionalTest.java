package com.server.other.optional;

import com.server.business.auth.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;

/**
 * @Description: TODO
 * @Author: yjy
 * @Date: 2025/3/15 10:14
 */
public class OptionalTest {

    @Test
    void fun() {
        Function<User, Long> function = new Function<User, Long>() {
            @Override
            public Long apply(User user) {
                return null;
            }
        };
    }

    public Optional<User> getOneUser(String name){
        if("啊哈".equals(name)){
            return Optional.of(new User());
        }
        else return Optional.empty();
    }

    @Test
    public void testOptional(){
        Optional<User> haha = getOneUser("haha");
        User user = haha.orElse(new User());
        System.out.println(user);
    }


}
