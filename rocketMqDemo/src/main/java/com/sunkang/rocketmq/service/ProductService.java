package com.sunkang.rocketmq.service;

import com.sunkang.rocketmq.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 生产者
 */
@Slf4j
@Service
public class ProductService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private static final String TOPIC = "first-topic";

    public void send() {
        Message<User> msg = MessageBuilder
                .withPayload(User.builder().name("你好,孙康" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build())
                .build();
        //普通消息
        rocketMQTemplate.convertAndSend(TOPIC, msg);
//        rocketMQTemplate.convertAndSend("first-topic", User.builder().name("你好,孙康" + System.currentTimeMillis()).build());
//        rocketMQTemplate.send("first-topic",
//                MessageBuilder
//                        .withPayload(User.builder().name("你好,孙康" + System.currentTimeMillis()).build())
//                        .build()
//        );

        //异步消息
//        rocketMQTemplate.asyncSend(TOPIC, msg, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                log.info("发送成功：{}", sendResult.toString());
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                log.error("发送失败,{}", msg, throwable);
//            }
//        });
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//        }
//        //延迟消息
//        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC, msg, 3000, 4);
//        log.info(sendResult.toString());

        //顺序消息,同一个hashKey到同一个队列，同一个队列才能保证顺序，消费者得设置为顺序消费，默认为并发消费
//        rocketMQTemplate.syncSendOrderly(TOPIC,msg,"key");


        //设置事务消息监听器
//        ((TransactionMQProducer)rocketMQTemplate.getProducer()).setTransactionListener(new TransactionMq());
//        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(TOPIC, msg, msg.getPayload().getName());
//        log.info(transactionSendResult.toString());
    }


}
