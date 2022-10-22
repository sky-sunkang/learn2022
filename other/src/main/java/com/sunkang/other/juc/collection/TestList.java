package com.sunkang.other.juc.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class TestList {
    public static void main(String[] args) throws InterruptedException {
        //普通list
        List<Integer> list=new ArrayList();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(()->{
                list.add(finalI);
            }).start();
        }

        //juc list
        List<Integer> copyList=new CopyOnWriteArrayList();

        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(()->{
                copyList.add(finalI);
            }).start();
        }


        TimeUnit.SECONDS.sleep(3);
        System.out.println(list.size());
        System.out.println(copyList.size());
    }
}
