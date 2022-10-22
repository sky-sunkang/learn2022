package com.sunkang.other.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Test03 {
    public static void main(String[] args) {
//        //Integer超过-127到128会有问题，因为Integer只会缓存该区间的，超过了则每次新建，就不是相同的对象了
//        Integer i1 = 2;
//        Integer i2 = 2;
//        //输出为true
//        System.out.println(i1 == i2);
//        Integer i3 = 1000;
//        Integer i4 = 1000;
//        //输出为false，Integer类型
//        System.out.println(i3 == i4);
//        //输出为true，Integer类型最好使用equals对比
//        System.out.println(i3.equals(i4));

        //实际项目中可能是User等对象
        AtomicStampedReference<Integer> atomic = new AtomicStampedReference<>(1, 1);
        new Thread(() -> {
            int stamp = atomic.getStamp();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomic.compareAndSet(1, 2, stamp, ++stamp));
        }).start();

        new Thread(() -> {
            int stamp = atomic.getStamp();
            System.out.println(atomic.compareAndSet(1, 2, stamp, ++stamp));
            System.out.println(atomic.compareAndSet(2, 1, stamp, ++stamp));
        }).start();

        while (Thread.activeCount() != 1) ;
        //1->3，第一个线程更新失败因为版本已经改变
        System.out.println(atomic.getReference() + "->" + atomic.getStamp());
    }
}
