package com.sunkang.other.completableFuture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 异步线程也可能是同一个线程，因为上一个线程可能空闲
 * 1、acceptEither 谁先执行完就执行谁，然后下一步，时间短的任务可能会执行一半
 * acceptEitherAsync 异步新起线程
 * <p>
 * 2、thenApply 顺序执行，会拿到上一个任务的结果 同一个CompletableFuture
 * thenApplyAsync 异步线程
 * <p>
 * 3、thenCombine 组合，可拿多个异步任务结果
 * thenCombineAsync
 * <p>
 * 4、thenCompose 顺序执行，会拿到上一个任务的结果 生成一个新的CompletableFuture。
 * thenComposeAsync
 */
@Slf4j
public class Test2 {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();


        // 1、acceptEither
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            print("1");
//            sleep(110);
//            return "1";
//        }).acceptEither(CompletableFuture.supplyAsync(() -> {
//            print("2");
//            sleep(100);
//            return "2";
//        }), new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                print("3");
//            }
//        });
//        voidCompletableFuture.join();


        // 2.thenApply
//        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            sleep(100);
//            print("1");
//            return "1";
//        }).thenApplyAsync((str) -> {
//            sleep(10);
//            print("2");
//            return str + "2";
//        });
//        log.info(stringCompletableFuture.join());

        //3、thenCombine
//        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            sleep(200);
//            print("1");
//            return "1";
//        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
//            sleep(100);
//            print("2");
//            return "2";
//        }), (str1, str2) -> {
//            print(str1 + str2);
//            return str1 + str2;
//        });
//        stringCompletableFuture.join();

        //4、thenCompose
//        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            print("1");
//            return "sunkang";
//        }).thenCompose(str -> CompletableFuture.supplyAsync(() -> {
//            print(str + str.toUpperCase(Locale.ROOT));
//            return str + str.toUpperCase(Locale.ROOT);
//        }));
//        stringCompletableFuture.join();
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        List<CompletableFuture<Integer>> futureList = list.stream()
                .map(x -> CompletableFuture.supplyAsync(() -> x + 1))
                .collect(Collectors.toList());

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
        log.info(String.valueOf(voidCompletableFuture.join()));

        stopWatch.stop();
        log.info("耗时：{}", stopWatch.getLastTaskTimeMillis());

    }


    public static void print(String msg) {
        log.info("id:{},name:{},msg:{}", Thread.currentThread().getId(), Thread.currentThread().getName(), msg);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
