package com.demos.admin;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/4 11:46
 */
public class RabbitAdminExample {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring-rabbit-admin.xml");
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        FanoutExchange fanoutExchange = new FanoutExchange("created-fanout-exchange", true, true);
        rabbitAdmin.declareExchange(fanoutExchange);
        System.out.println("exchange created.");

        Queue queue1 = new Queue("created-queue1", true, false, true);
        rabbitAdmin.declareQueue(queue1);
        System.out.println("queue created.");

        Binding binding = BindingBuilder.bind(queue1).to(fanoutExchange);
        rabbitAdmin.declareBinding(binding);
        System.out.println("binding created.");

        context.close();

    }
}
