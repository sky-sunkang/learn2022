package com.sunkang.other.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test01 {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueue blockingQueue = new ArrayBlockingQueue<String>(2);
        //1、会抛异常的存取
        blockingQueue.add("1");
        blockingQueue.add("2");
        //java.lang.IllegalStateException: Queue full
        //blockingQueue.add("3");
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //java.util.NoSuchElementException
        //blockingQueue.remove();

        System.out.println("===");

        //2 不抛出异常，可带超时时间
        System.out.println(blockingQueue.offer("1"));
        System.out.println(blockingQueue.offer("2"));
        System.out.println(blockingQueue.offer("3"));
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        System.out.println("===");

        //3 阻塞(队列满了之后，存和取都会被阻塞等待)，可带超时时间
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println("开始put：" + finalI);
                    blockingQueue.put(Thread.currentThread().getName() + "" + finalI);
                    System.out.println("结束put：" + finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "=》" + blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }
}
