package com.demos.ttl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;

/**
 * 通过设置死信exchange完成延时消息
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/5 9:49
 */
public class RabbitTTLExample {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring-rabbit-ttl.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        rabbitTemplate.convertAndSend("", "ttl-queue", "发送TTL消息");

        int waitSeconds = 0;

        while(true){
            Thread.sleep(1000);
            System.out.println(LocalDateTime.now());
            waitSeconds += 1;
            if(waitSeconds > 6){
                break;
            }
        }
        context.close();

    }
}
