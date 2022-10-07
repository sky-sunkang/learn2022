package com.sunkang.search.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping(value = "test")
    public String test() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error("test error",e);
        }
        return "测试getway";
    }

    @GetMapping("/testOOM")
    public void testOOM() {
        List<String> list = new ArrayList<String>();
        while (true) {
            list.add("测试jvm");
        }
    }
}
