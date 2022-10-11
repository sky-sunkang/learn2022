package com.sunkang.other.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * 接口式编程
 * 定义一个接口，只有一个方法，则为接口式编程
 */
public class Test {

    public static void main(String[] args) {
        ICar car = () -> {
            System.out.println("跑跑跑");
        };
        car.run();

        Like like = new Like(1);
        System.out.println(like.like(i -> i));

        List<String> list = Arrays.asList("a", "b");
        list.stream().forEach(x -> System.out.println(x));
    }
}

/**
 * 函数式接口
 */
interface ICar {
    void run();
}

/**
 * 函数式接口
 */
interface Function {
    int run(int i);

    default int run() {
        return 2;
    }

    static int stat() {
        return 3;
    }
}

/**
 * 函数式传参，类似jdk8 stream
 */
class Like {

    private int i;

    public Like(int i) {
        this.i = i;
    }

    public int like(Function fun) {
        System.out.println(fun.run());
        System.out.println(Function.stat());
        return fun.run(i);
    }
}