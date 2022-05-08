package com.sunkang.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * 消费者
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "test",
        topic = "first-topic",
        maxReconsumeTimes = 3,
        consumeMode = ConsumeMode.CONCURRENTLY,
        consumeThreadNumber = 5,
        consumeTimeout = 3000
)
public class ConsumerService implements RocketMQListener<MessageExt> {


    @Override
    public void onMessage(MessageExt ext) {
        log.info("id:{},queueId:{},body:{}", ext.getMsgId(), ext.getQueueId(), new String(ext.getBody()));

        //重试三次进入死信队列
//        throw new RuntimeException("第" + (ext.getReconsumeTimes() + 1) + "次消费失败了");
    }
}
