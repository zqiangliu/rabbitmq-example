package com.demos.ttl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/5 10:13
 */
public class DeadMessageConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("DeadMessageConsumer接收到消息：" + new String(message.getBody()));
    }
}
