package com.sunkang.other.juc.collection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class TestSet {

    public static void main(String[] args) {
        //普通set
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(() -> set.add(finalI)).start();
        }

        //Juc Set
        Set<Integer> copySet=new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(() -> copySet.add(finalI)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(set.size());
        System.out.println(copySet.size());
//        9993
//        10000
    }
}
