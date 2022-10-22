package com.sunkang.other.volatile1;

import java.util.concurrent.TimeUnit;

public class Test02 {
    private static boolean ready=false;
    private static int i=0;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (!ready){
                Thread.yield();
            }
            System.out.println(i);//打印1
        }).start();
        TimeUnit.SECONDS.sleep(1);
        i=11;
        ready=true;
    }
}
