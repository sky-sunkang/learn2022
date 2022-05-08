package com.sunkang.rocketmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {


    @Bean
    public User2 user2() {
        User2 user = new User2();
        user.setName("2");
        return user;
    }

    @Bean
    public User1 user1() {
        User1 user1 = new User1();
        user1.setName("1");
        return user1;
    }
}
