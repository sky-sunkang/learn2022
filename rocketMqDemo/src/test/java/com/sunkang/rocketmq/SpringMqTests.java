package com.sunkang.rocketmq;

import com.sunkang.rocketmq.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@SpringBootTest
class SpringMqTests {
    @Autowired
    private ProductService productService;


    @Test
    public void test(){
        productService.send();
    }
}
