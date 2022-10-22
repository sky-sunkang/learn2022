package com.sunkang.other.juc.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class TestMap {
    public static void main(String[] args) throws InterruptedException {

        //普通map
        Map<Integer, Integer> map=new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(()->map.put(finalI, finalI)).start();
        }

        //juc Map
        Map<Integer, Integer> concurrentHashMap=new ConcurrentHashMap();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(()->concurrentHashMap.put(finalI, finalI)).start();
        }

        TimeUnit.SECONDS.sleep(3);

        System.out.println(map.size());
        System.out.println(concurrentHashMap.size());
//        9987
//        10000
    }
}
