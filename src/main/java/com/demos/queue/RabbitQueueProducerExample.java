package com.demos.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/3 16:33
 */
public class RabbitQueueProducerExample {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-rabbit-direct-producer.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        rabbitTemplate.convertAndSend("", "queue.test", "direct model发送测试息");
        context.close();

    }
}
