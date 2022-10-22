package com.sunkang.other.pool;

import java.util.concurrent.*;

public class Test02 {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                10,
                TimeUnit.SECONDS,
                blockingQueue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 1; i <=8 ; i++) {
            int finalI = i;
            threadPoolExecutor.execute(()->{
                System.out.println(Thread.currentThread().getName()+"->"+ finalI);
            });
        }

    }
}
