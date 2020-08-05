package com.demos.confirm.callback;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/5 16:12
 */
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("confirm, ack=" + ack + ", cause=" + cause);
    }
}
