package com.sunkang.other.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static void main(String[] args) throws InterruptedException {
        Pool pool = new Pool();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(() -> {
                pool.add(finalI);

            }).start();
        }

        Thread.sleep(5000);
        pool.printTotal();
    }
}

class Pool {
    private static final ReentrantLock lock = new ReentrantLock();
    private static List<Integer> list = new ArrayList<>();

    private static Integer count = 0;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static CopyOnWriteArrayList cowList = new CopyOnWriteArrayList();

    public void add(Integer str) {
        lock.lock();
        try {
            list.add(str);
        } finally {
            lock.unlock();
        }
        cowList.add(str);
        count++;
        atomicInteger.getAndIncrement();
    }


    /**
     * 打印并发结果
     * 1:10000
     * 2:10000
     * 3:9998
     * 4:10000
     */
    public void printTotal(){
        System.out.println( "1:" + list.size());
        System.out.println( "2:" + cowList.size());
        System.out.println( "3:" + count);
        System.out.println( "4:" + atomicInteger.get());
    }
}