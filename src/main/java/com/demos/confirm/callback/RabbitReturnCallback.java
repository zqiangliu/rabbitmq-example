package com.demos.confirm.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/5 16:09
 */
public class RabbitReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("return, code=" + replyCode + ", exchange=" + exchange + ", routingKey=" + routingKey);
    }
}
