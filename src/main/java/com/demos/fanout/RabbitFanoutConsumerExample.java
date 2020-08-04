package com.demos.fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/3 17:43
 */
public class RabbitFanoutConsumerExample {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring-rabbit-fanout-consumer.xml");

    }
}
