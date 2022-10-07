package com.sunkang.redisdemo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LinkedHashMap有序的，通过记录下个节点来保证顺序，继承HashMap
 * 通过链表map实现超长之后清除越久，越老未使用的数据
 */
public class LinkedHashMapLru extends LinkedHashMap {

    int maxCapacity = 16;

    /**
     * 初始化设置最大存储值
     *
     * @param maxCapacity
     */
    public LinkedHashMapLru(int maxCapacity) {
        //初始化长度，复核系数，get完后放到链表尾部
        super(5, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }

    /**
     * 超过最大长度，则删除链表的第一个
     *
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > maxCapacity;
    }

    public static void main(String[] args) {
        LinkedHashMapLru linkedHashMapLru = new LinkedHashMapLru(10);
        for (int i = 1; i < 100; i++) {
            if(i==95){
                System.out.println(linkedHashMapLru.get("87"));
            }
            linkedHashMapLru.put(i + "", i);
        }

        System.out.println(linkedHashMapLru.keySet().stream().collect(Collectors.joining(",")));
    }
}
