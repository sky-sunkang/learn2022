package com.sunkang.other.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 静态方法锁的是class，非静态方法是锁的对象，所以不是同一把锁
 打电话
 发短信
 */
public class Test4 {

    public static void main(String[] args) {
        Phone4 phone = new Phone4();
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


class Phone4 {

    public static synchronized void sendMsg() {
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
