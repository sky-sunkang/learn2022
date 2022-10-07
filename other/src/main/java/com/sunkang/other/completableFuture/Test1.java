package com.sunkang.other.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * 简单并行编程
 */
@Slf4j
public class Test1 {
    public static void main(String[] args) {
//        //串行异步任务
        log.info("我要吃饭");
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            log.info("厨师炒菜,{}", Thread.currentThread());
            int i = 1 / 0;
            return "豆角炒肉";
        }).whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (throwable == null) {
                    sleep(1000);
                    log.info("服务员上菜,{}", Thread.currentThread());
                } else {
                    log.error("炒菜出错了", throwable);
                }
            }
        });
        log.info("张三在玩王者荣耀");
        log.info(stringCompletableFuture.join());
        log.info("张三开始吃饭");


        //并行任务
//        log.info("我要吃饭");
//        CompletableFuture<String> uCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            sleep(1000);
//            log.info("厨师1炒菜,{}", Thread.currentThread());
////            int i = 1 / 0;
//            return "豆角炒肉";
//        }).thenCombine(CompletableFuture.supplyAsync(() -> {
//            sleep(1000);
//            log.info("厨师2炒菜,{}", Thread.currentThread());
//            return "西红柿炒蛋";
//        }), (a, b) -> {
//            log.info("出餐，{}，{}", a, b);
//            return a + ":" + b;
//        });
//        log.info("张三在玩王者荣耀");
//        log.info(uCompletableFuture.join());
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
