package com.sunkang.other.jvm.demo1;

/**
 * jvm
 * -Xms10m -Xmx10m -XX:+PrintGCDetails
 */
public class Demo01 {
    public static void main(String[] args) {
        //输出内存信息
        long memory = Runtime.getRuntime().maxMemory();
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        System.out.println(memory / 1024 / 1024 +"m");
        System.out.println(total / 1024 / 1024 +"m");
        System.out.println(free / 1024 / 1024 +"m");

        //1、栈溢出 stackOverflow
//        new Demo01().a();
        //2、OutOfMemory fullGC后不能腾出空间，就oom
        String str = "abcdefg";
        while (true) {
            str += str;
        }
    }

    public void a() {
        b();
    }

    public void b() {
        a();
    }
}
