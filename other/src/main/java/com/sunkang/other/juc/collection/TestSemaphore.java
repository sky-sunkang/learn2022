package com.sunkang.other.juc.collection;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量
 */
public class TestSemaphore {

    public static void main(String[] args) {
        Station station=new Station();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                station.inStation();
                try {
                    //业务代码
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                station.outStation();
            }).start();
        }
    }
}

class Station{
    //车站容量5
    Semaphore semaphore=new Semaphore(5);

    /**
     * 进站
     */
    public void inStation() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"进站了");
    }

    /**
     * 出站
     */
    public void outStation(){
        System.out.println(Thread.currentThread().getName()+"出站了");
        semaphore.release();
    }


}