package com.git.onedayrex.rocketmq.customer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class Customer {
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);


    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer1");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeMessageBatchMaxSize(10);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("ccs", "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            if (msgs != null && !msgs.isEmpty()) {
                for (MessageExt msg : msgs) {
                    logger.info("receive message==>{}",new String(msg.getBody(), Charset.defaultCharset()));
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        try {
            consumer.start();
            logger.info("---consumer starting---");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
