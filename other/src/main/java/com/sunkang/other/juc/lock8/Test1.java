package com.sunkang.other.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 1、非静态方法锁的是this，调用方法的对象，对象是一个，所以会互斥
 * 发短信
 * 打电话
 */
public class Test1 {

    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendMsg();
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.call();
        }).start();
    }

}


class Phone {

    public synchronized void sendMsg() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call() {
        System.out.println("打电话");
    }
}
