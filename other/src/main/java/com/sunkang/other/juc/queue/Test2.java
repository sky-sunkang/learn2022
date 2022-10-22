package com.sunkang.other.juc.queue;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(3, ()->{
            System.out.println("全部执行完毕");
        });

        for (int i = 0; i < 8; i++) {
            int finalI = i;
            TimeUnit.SECONDS.sleep(1);
            new Thread(()->{
                try {
                    System.out.println(finalI +"=>"+Thread.currentThread().getName());
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
