package com.git.onedayrex.rocketmq.product;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class Productor{
    private static final Logger logger = LoggerFactory.getLogger(Productor.class);


    public static void main(String[] args) {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("productGroupFirst");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        try {
            defaultMQProducer.start();
            while (true) {
                String msg = String.valueOf(RandomUtils.nextLong(0, 99999));
                Message message = new Message("ccs","TestTag","1",msg.getBytes(Charset.forName("UTF-8")));
                SendResult send = defaultMQProducer.send(message);
                logger.info("send result==>{}", JSON.toJSONString(send));
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
        defaultMQProducer.shutdown();
    }
}
