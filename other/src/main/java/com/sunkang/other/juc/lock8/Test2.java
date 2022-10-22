package com.sunkang.other.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 非静态方法锁的是this，调用方法的对象，对象不是同一个，所以不会互斥
 * 打电话
 * 发短信
 */
public class Test2 {

    public static void main(String[] args) {
        Phone1 phone1 = new Phone1();
        Phone1 phone2 = new Phone1();
        new Thread(() -> {
            phone1.sendMsg();
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone2.call();
        }).start();
    }

}


class Phone1 {

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
