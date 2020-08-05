package com.demos.ttl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/5 10:13
 */
public class MessageConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("MessageConsumer接收到消息：" + new String(message.getBody()));
    }
}
