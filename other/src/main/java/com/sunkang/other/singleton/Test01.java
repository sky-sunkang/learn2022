package com.sunkang.other.singleton;

/**
 * 饿汉模式
 */
public class Test01 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                User inst = User.getInst();
                System.out.println(inst);
            }).start();
        }
    }
}
class User {
    private static User user = new User();
    private User() {
    }
    public static User getInst() {
        return user;
    }
}