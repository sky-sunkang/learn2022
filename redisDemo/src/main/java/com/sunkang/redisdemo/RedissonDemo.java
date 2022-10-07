package com.sunkang.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.LinkedHashMap;

/**
 * redis布隆过滤器
 */
public class RedissonDemo {
    public static void main(String[] args) {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        // 相当于创建了redis的连接
        RedissonClient redisson = Redisson.create(config);

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("myBloomFilter");
        //初始化,预计元素数量为100000000,期待的误差率为4%
        bloomFilter.tryInit(10000,0.01);
        long l=System.currentTimeMillis();
        for (int i = 1; i <= 10000; i++) {
            bloomFilter.add(String.valueOf(i));
        }

        int count = 0;
        for (int i = 9000; i <= 11000; i++) {
            if (bloomFilter.contains(String.valueOf(i))) {
                count++;
            }
        }
//        匹配数：1024
//        误差数：0.024
//        耗时6567
        System.out.println("匹配数：" + count);
        System.out.println("误差数：" + 1.0 * (count - 1000) / 1000);
        System.out.println("耗时"+(System.currentTimeMillis()-l));
        redisson.shutdown();
    }
}
