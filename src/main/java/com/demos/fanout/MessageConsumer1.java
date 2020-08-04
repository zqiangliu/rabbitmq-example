package com.demos.fanout;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MessageConsumer1 implements MessageListener {
    public void onMessage(Message message) {
        System.out.println("MessageConsumer1接收到消息：" + new String(message.getBody())
        );
    }
}
