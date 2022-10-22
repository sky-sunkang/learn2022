package com.sunkang.other.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test01 {

    public static void main(String[] args) {
        //池大小为1
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //固定长度
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //非固定长度，最大长度Integer.MAX_VALUE
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "->" + finalI);
            });
        }
    }
}
