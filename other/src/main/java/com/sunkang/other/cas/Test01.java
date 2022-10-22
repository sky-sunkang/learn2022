package com.sunkang.other.cas;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 比较并更新
 */
public class Test01 {

    public static void main(String[] args) {

        AtomicInteger at = new AtomicInteger(0);
//        System.out.println(at.compareAndSet(0, 1));
//        System.out.println(at.get());
//
//        System.out.println(at.compareAndSet(0, 1));
//        System.out.println(at.get());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, Runtime.getRuntime().availableProcessors(), 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()

        );

        try {

            for (int i = 0; i < 1000; i++) {
                int finalI = i;
                executor.execute(() -> {
                    //自选更新
                    while (!at.compareAndSet(finalI, finalI + 1)) ;
                    System.out.println("更新成功" + (finalI + 1));
                });
            }
        } finally {
            executor.shutdown();
        }
        while (Thread.activeCount() != 1) {
        }
        System.out.println("全部更新完成，最终结果：" + at.get());


    }
}
