package com.demos.confirm;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.Random;

/**
 * 手动确认需要使用ChannelAwareMessageListener接口
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/5 15:28
 */
public class ConfirmedConsumer implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("ConfirmedConsumer接收到消息：" + new String(message.getBody()));
        //手动确认
        Random rand = new Random();
        int i = rand.nextInt(10);
        if(i < 5){
            //否定消息，requeue=true重写添加到队列，重新消费
            System.out.println("nack");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }else{
            System.out.println("ack");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
