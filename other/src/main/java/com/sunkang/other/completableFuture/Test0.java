package com.sunkang.other.completableFuture;


import cn.hutool.core.date.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 测试异步编程
 */
public class Test0 {
    public static void main(String[] args) {
//        test0();
        test1();

    }

    public static void test1() {
        StopWatch stopWatch = new StopWatch("异步任务");
        stopWatch.start("阶段1");
        Executor executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<List<Integer>>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            futures.add(
                    CompletableFuture.supplyAsync(() -> {
                        if (finalI == 2) {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                        //异常如果不捕获join后的代码将不被执行，但是每个任务还是会被执行完
                        if (finalI == 5) {
                            int a = 1 / 0;
                        }
                        List<Integer> list = new ArrayList<>();
                        for (int j = 0; j < 10; j++) {
                            list.add(finalI * 10 + j);
                        }
                        System.out.println(finalI + "执行完毕，线程id:" + Thread.currentThread().getId() + "，线程名称" + Thread.currentThread().getName());
                        return list;
                    }, executor).exceptionally((e)->{
                        e.printStackTrace();
                        return new ArrayList<>();
                    })
            );
        }
        stopWatch.stop();
        stopWatch.start("阶段2");
        List<Integer> strings = futures.stream().map(CompletableFuture::join).reduce((x, y) -> {
            x.addAll(y);
            return x;
        }).get();
        stopWatch.stop();
        System.out.println(stopWatch.toString());
        System.out.println(strings);
    }

    public static void test0() {

        System.out.println(0);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            System.out.println(1);
            return "aaa";
        }).exceptionally((e) -> {
            e.printStackTrace();
            return "bbb";
        });
        System.out.println(2);
        System.out.println(future.join());
        System.out.println(3);
    }
}
