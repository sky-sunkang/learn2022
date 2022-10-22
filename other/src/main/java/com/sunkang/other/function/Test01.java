package com.sunkang.other.function;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Test01 {
    public static void main(String[] args) {
        consumer();
        fun();
        predicate();
        supplier();
    }

    public static void supplier() {
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt();
            }
        };
        System.out.println(supplier.get());

        Supplier<Integer> supplier1 = () -> {
            return new Random().nextInt();
        };
        System.out.println(supplier1.get());

    }

    public static void predicate() {
        Predicate predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer o) {
                if (o > 10) {
                    return true;
                }
                return false;
            }
        };
        System.out.println(predicate.test(20));

        Predicate<Integer> predicate1 = (x) -> {
            if (x > 10) {
                return true;
            }
            return false;
        };
        System.out.println(predicate1.test(5));
    }

    public static void fun() {
        Function function = new Function<String, String>() {
            @Override
            public String apply(String o) {
                return o.toUpperCase();
            }
        };
        System.out.println(function.apply("abcd"));

        Function<String, String> function1 = (x) -> {
            return x.toUpperCase();
        };
        System.out.println(function1.apply("abc"));
    }

    public static void consumer() {
        //历史版
        Consumer consumer = new Consumer<String>() {
            @Override
            public void accept(String o) {
                System.out.println(o);
            }
        };
        consumer.accept("哈哈哈");

        //表达式
        Consumer consumer1 = (o) -> {
            System.out.println(o);
        };
        consumer1.accept("哈哈哈");
    }
}
