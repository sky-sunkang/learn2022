package com.sunkang.other.singleton;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * 懒汉模式 双校验
 */
public class Test02 {
    public static void main(String[] args) throws Exception{
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                User2 user = User2.getInst();
                System.out.println(user);
            }).start();
        }
        //懒汉可反射破解
        Constructor<?> constructor = Arrays.stream(User2.class.getDeclaredConstructors()).findFirst().get();
        constructor.setAccessible(true);
        Object o = constructor.newInstance();
        System.out.println(o);
    }
}
class User2 {
    private volatile static User2 user = null;
    private User2() {
        //该方法还是可被反射破解，先设置user为空，再调用该构造器
        if (user != null) {
            throw new RuntimeException("请不要使用反射破解单例模式");
        }
    }
    public static User2 getInst() {
        if (user == null) {
            synchronized (User2.class) {
                if (user == null) {
                    user = new User2();
                }
            }
        }
        return user;
    }
}
