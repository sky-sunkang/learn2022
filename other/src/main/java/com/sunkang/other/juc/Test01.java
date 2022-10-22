package com.sunkang.other.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模式
 */
public class Test01 {
    public static void main(String[] args) {

        Data data = new Data();
//        new Thread(() -> {
//            try {
//                for (int i = 0; i < 10; i++) {
//                    data.add();
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "A").start();
//        new Thread(() -> {
//            try {
//                for (int i = 0; i < 10; i++) {
//                    data.reduce();
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "B").start();
//        new Thread(() -> {
//            try {
//                for (int i = 0; i < 10; i++) {
//                    data.add();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "C").start();
//        new Thread(() -> {
//            try {
//                for (int i = 0; i < 10; i++) {
//                    data.reduce();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "D").start();


//        lock版
        Data2 data2 = new Data2();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data2.add();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data2.reduce();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data2.add();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    data2.reduce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "D").start();

    }


}

class Data {

    private int count = 0;

    public synchronized void add() throws InterruptedException {
        while (count != 0) {
            this.wait();
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "->" + count);
        this.notifyAll();
    }

    public synchronized void reduce() throws InterruptedException {
        while (count == 0) {
            this.wait();
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "->" + count);
        this.notifyAll();
    }

}

/**
 * juc版
 */
class  Data2{

    private int count=0;

    private static Lock lock=new ReentrantLock();
    Condition condition = lock.newCondition();



    public void add() throws InterruptedException {
        lock.lock();


        try {
            while (count != 0) {
                condition.await();
            }
            count++;
            System.out.println(Thread.currentThread().getName() + "->" + count);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void reduce() throws InterruptedException {
        lock.lock();

        try {
            while (count == 0) {
                condition.await();
            }
            count--;
            System.out.println(Thread.currentThread().getName() + "->" + count);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
