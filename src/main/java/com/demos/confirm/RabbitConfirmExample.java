package com.demos.confirm;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/5 15:07
 */
public class RabbitConfirmExample {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-rabbit-confirm-producer.xml");
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        //
        while(true){
            System.out.println("请输入消息内容：");
            Scanner in = new Scanner(System.in);
            in.useDelimiter("\n");
            String next = in.next();
            if ("quit".equals(next)) {
                break;
            }
            rabbitTemplate.convertAndSend("", "queue.test", next);

        }

        System.out.println("do other work...");
        context.close();
    }
}
