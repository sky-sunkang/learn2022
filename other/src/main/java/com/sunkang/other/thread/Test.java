package com.sunkang.other.thread;


public class Test {
    public static void main(String[] args) throws InterruptedException {
        //1、join插队，线程调用join后其他线程会等待该线程执行完成
        {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 100; i++) {
                    System.out.println("joinThread:" + i);
                }
            });
            thread.start();
            for (int i = 0; i < 100; i++) {
                if (i == 10) {
                    thread.join();
                }
                System.out.println("main:" + i);
            }
        }

        //2、线程状态，该例子只能模拟其中部分状态，所有状态对应Thread.State
        {
            Thread thread = new Thread(() -> {
                System.out.println("开始跑了");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("跑完了");
            });
            System.out.println(thread.getState());

            thread.start();
            while (!thread.getState().equals(Thread.State.TERMINATED)) {
                Thread.sleep(100);
                System.out.println(thread.getState());
            }
        }

        //3、优先级，并不完全按照优先级来
        {
            // 8个核
            System.out.println(Runtime.getRuntime().availableProcessors());
            System.out.println(String.format("%s 优先级 %s", Thread.currentThread().getName(), Thread.currentThread().getPriority()));
            for (int i = 0; i < 10; i++) {
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(String.format("%s 优先级 %s", Thread.currentThread().getName(), Thread.currentThread().getPriority()));
                });
                thread.setPriority(i + 1);
                thread.start();
            }
        }
    }
}
