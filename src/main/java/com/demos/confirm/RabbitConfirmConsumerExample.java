package com.demos.confirm;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/3 16:33
 */
public class RabbitConfirmConsumerExample {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring-rabbit-confirm-consumer.xml");

    }
}
