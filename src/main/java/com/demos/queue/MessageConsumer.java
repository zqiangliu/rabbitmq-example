package com.demos.queue;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MessageConsumer implements MessageListener {
    public void onMessage(Message message) {
        System.out.println("接收到消息：" + new String(message.getBody())
        );
    }
}
