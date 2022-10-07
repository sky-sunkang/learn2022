package com.sunkang.redisdemo;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * //ReentrantLock：
 * 	//可公平锁可非公平锁，公平锁：按照时间先后顺序获取锁
 * 	//获取锁时可设置超时时间，超时则不获取锁
 * //synchronized：非公平锁
 */
public class Test extends Thread {
    //默认为异步锁
    private static ReentrantLock lock = new ReentrantLock(false);
    private static int index = 0;

    public static void add(int finalI) {
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                boolean tryLock = false;
                try {
                    tryLock = lock.tryLock(1000, TimeUnit.MILLISECONDS);
                    if (tryLock) {
                        System.out.println(Thread.currentThread().getName() + "|" + (++index) + "|" + finalI);
                    } else {
                        System.out.println("未获取到锁  " + Thread.currentThread().getName() + "|" + index + "|" + finalI);
                    }
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    if (tryLock) {
                        lock.unlock();
                    }
                }
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            add(i);
        }
    }
}
