package com.atguigu.gmall.mq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Symon
 * @version 1.0
 * @className MqTestReceiver
 * @date 2021/2/26 18:58
 */
@Component
public class MqTestReceiver {

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "a",autoDelete = "false"),
            value = @Queue(value = "c",autoDelete = "false"),
            key = {"b"}
    ))
    public void testReceiver(Channel channel, Message message, String c) {
        System.out.println("abc的监听");
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            channel.basicNack(deliveryTag,false,true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
