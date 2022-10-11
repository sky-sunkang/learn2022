package com.sunkang.other.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static void main(String[] args) throws InterruptedException {
        Pool pool = new Pool();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            new Thread(() -> {
                pool.add(finalI);

            }).start();
        }

        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " 1 " + pool.get().size());
        System.out.println(Thread.currentThread().getName() + " 2 " + pool.getCow().size());
    }
}

class Pool {
    private static final ReentrantLock lock = new ReentrantLock();
    private static List<Integer> list = new ArrayList<>();

    private static CopyOnWriteArrayList cowList=new CopyOnWriteArrayList();

    public void add(Integer str) {
        lock.lock();
        try{
            list.add(str);
//            System.out.println(Thread.currentThread().getName() + " 1 " + list.size());
        }finally {
            lock.unlock();
        }
        cowList.add(str);
//        System.out.println(Thread.currentThread().getName() + " 2 " + cowList.size());
    }


    public List<Integer> get() {
        return list;
    }

    public CopyOnWriteArrayList getCow(){
        return cowList;
    }
}