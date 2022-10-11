package com.sunkang.other.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * synchronized默认锁this对象，可用代码块锁定需要锁定的对象
 *
 * 锁静态方法、静态变量、Class则是锁class
 *
 */
public class TestSyn {
    private static List<String> list = new ArrayList<>();

    private static Integer j=0;

    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(() -> {
                TestSyn testSyn=new TestSyn();
                //1、锁对象，不同对象无法锁住
//                synchronized (testSyn){
//                    testSyn.addList(finalI + "");
//                    j++;
//                }

                //2、锁静态方法、静态变量、class则是锁class
                synchronized (j){
                    testSyn.addList(finalI + "");
                    j++;
                }

            }).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new TestSyn().list.size());
        System.out.println(new TestSyn().j);
    }


    public void addList(String str) {
        list.add(str);
    }

}
