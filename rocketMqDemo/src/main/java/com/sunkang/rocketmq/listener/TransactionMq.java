package com.sunkang.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 事务消息监听器
 */
@Slf4j
public class TransactionMq implements TransactionListener {
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("body:{}", new String(message.getBody()));
        log.info("name:{}", o);
        //写业务代码
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }



        // 返回状态，提交还是回滚
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * mq主动检查状态
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
