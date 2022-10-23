package com.sunkang.other.cas;

import cn.hutool.core.date.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义自旋锁
 * 通过多次对比执行(注释例外一个代码块，单独执行)会发现自定义自旋锁更快
 * 1226:4838
 * 1366:5302
 */
public class DefineLock {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("自定义锁");
        //自定义自旋锁
        CasLock casLock = new CasLock();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(() -> {
                casLock.lock();
                try {
                    list.add(finalI);
                } finally {
                    casLock.unlock();
                }
            }).start();
        }

        while (Thread.activeCount() != 1) {
            Thread.yield();
        }
        System.out.println(list.size());
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());

        stopWatch.start("可重入锁");
        //可重入锁
        List<Integer> list1 = new ArrayList<>();
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(() -> {
                reentrantLock.lock();
                try {
                    list1.add(finalI);
                } finally {
                    reentrantLock.unlock();
                }
            }).start();
        }

        while (Thread.activeCount() != 1) {
            Thread.yield();
        }
        System.out.println(list1.size());
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
    }
}

/**
 * 自定义自旋锁
 */
class CasLock {

    private volatile AtomicReference<Thread> atomicReference = new AtomicReference<>(null);

    /**
     * 加锁
     */
    public void lock() {
        //如果更新不成功，则表示有线程在占用,则无限while
        //更新成功则跳出循环
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {
            //让出执行
            Thread.yield();
        }
//        System.out.println(Thread.currentThread().getName() + "加锁");
    }
    /**
     * 解锁
     */
    public void unlock() {
//        System.out.println(Thread.currentThread().getName() + "解锁");
        atomicReference.compareAndSet(Thread.currentThread(), null);
    }
}