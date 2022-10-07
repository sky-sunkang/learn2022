package com.sunkang.redisdemo;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * guava过滤器
 */
public class BloomFilterCase {

    private static final int expectedInsertions = 10000;

    private static final double fpp = 0.01;

    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);


    public static void main(String[] args) {
        long l=System.currentTimeMillis();
        for (int i = 1; i <= 10000; i++) {
            bloomFilter.put(String.valueOf(i));
        }

        int count = 0;
        for (int i = 9000; i <= 11000; i++) {
            if (bloomFilter.mightContain(String.valueOf(i))) {
                count++;
            }
        }
//        匹配数：1010
//        误差数：0.01
//        耗时41
        System.out.println("匹配数：" + count);
        System.out.println("误差数：" + 1.0 * (count - 1000) / 1000);
        System.out.println("耗时"+(System.currentTimeMillis()-l));
    }
}
