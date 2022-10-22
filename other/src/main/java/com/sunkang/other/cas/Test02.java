package com.sunkang.other.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test02 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        //正常业务线程
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicInteger.compareAndSet(1, 2);
        }).start();

        //捣蛋线程
        new Thread(() -> {
            atomicInteger.compareAndSet(1, 2);
            atomicInteger.compareAndSet(2, 1);
        }).start();

        while (Thread.activeCount() != 1) ;
        System.out.println(atomicInteger.get());
    }

}
