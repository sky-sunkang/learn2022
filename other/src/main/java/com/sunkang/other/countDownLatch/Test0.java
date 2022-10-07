package com.sunkang.other.countDownLatch;

import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountDownLatch
 * 多线程，等待
 */
public class Test0 {
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch("测试countDownLatch");
        stopWatch.start("开启线程任务");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    if (finalI == 2) {
                        Thread.sleep(1000);
                    }

                    System.out.println(Thread.currentThread().getName());
                    if (finalI == 3) {
                        int a = 1 / 0;
                    }
                    count.getAndIncrement();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        stopWatch.stop();
        stopWatch.start("等待任务执行结果");
        countDownLatch.await(10, TimeUnit.MINUTES);
        System.out.println(count.get());
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
