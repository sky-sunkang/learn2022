package com.sunkang.other.diedLook;

import java.util.concurrent.TimeUnit;

public class Test01 {
    public static void main(String[] args) {
        new Thread(new User5("lock1","lock2")).start();
        new Thread(new User5("lock2","lock1")).start();
    }
}

class User5 extends Thread{
    private String lock1;
    private String lock2;
    public User5(String lock1, String lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }
    @Override
    public void run() {
        synchronized (lock2) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
            }
        }
    }
}
