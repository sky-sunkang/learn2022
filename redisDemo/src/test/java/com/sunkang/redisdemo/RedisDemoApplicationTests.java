package com.sunkang.redisdemo;

import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@SpringBootTest
class RedisDemoApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisBloomFilter<?> redisBloomFilter;

    //默认为异步锁
    private static ReentrantLock lock = new ReentrantLock(false);


    private static int index = 0;

    public void add(int finalI) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if(lock.tryLock(100, TimeUnit.MILLISECONDS)){

                        log.info("thread-name:{},index:{},i:{}", Thread.currentThread().getName(), ++index, finalI);
                    }else{
                        log.info("未获取到锁 thread-name:{},index:{},i:{}", Thread.currentThread().getName(), ++index, finalI);
                    }
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Throwable e) {
                    log.error("error", e);
                } finally {
                    lock.unlock();
                }

            }
        });
        thread.start();
    }

    @Test
    void testLock() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {

            this.add(i);
        }
    }

    @Test
    void testBloom(){
//        redisTemplate.opsForValue().setBit("key",1,false);
//
//        Boolean key = redisTemplate.opsForValue().getBit("key", 1);
//        log.info(String.valueOf(key));

        int expectedInsertions = 1000;
        double fpp = 0.1;
        redisBloomFilter.delete("bloom");
        BloomFilterHelper<CharSequence> bloomFilterHelper = new BloomFilterHelper<>(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);
        int j = 0;
        // 添加100个元素
        List<String> valueList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            valueList.add(i + "");
        }
        long beginTime = System.currentTimeMillis();
        redisBloomFilter.addList(bloomFilterHelper, "bloom", valueList);
        long costMs = System.currentTimeMillis() - beginTime;
        log.info("-----------------------------------------------------------------");
        log.info("布隆过滤器添加{}个值，耗时：{}ms", 100, costMs);
        for (int i = 0; i < 1000; i++) {
            boolean result = redisBloomFilter.contains(bloomFilterHelper, "bloom", i + "");
            if (!result) {
                j++;
            }
        }
        log.info("漏掉了{}个,验证结果耗时：{}ms", j, System.currentTimeMillis() - beginTime);
    }
}
