package com.sunkang.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * 死信消费者
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "dlq-test",
        topic = "%DLQ%test",
        maxReconsumeTimes = 3,
        consumeMode = ConsumeMode.CONCURRENTLY,
        consumeThreadNumber = 5,
        consumeTimeout = 3000
)
public class DlqConsumerService implements RocketMQListener<MessageExt> {


    @Override
    public void onMessage(MessageExt ext) {
        log.info("死信消费：id:{},queueId:{},body:{}", ext.getMsgId(), ext.getQueueId(), new String(ext.getBody()));
    }
}
