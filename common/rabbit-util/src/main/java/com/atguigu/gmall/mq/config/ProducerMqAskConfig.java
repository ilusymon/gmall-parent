package com.atguigu.gmall.mq.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Symon
 * @version 1.0
 * @className ProducerMqAskConfig
 * @date 2021/2/26 20:48
 */
@Component
public class ProducerMqAskConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    @Resource
    RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void init() {
        // 将确认类放入rabbitTemplate
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息的发送状态是" + ack + " 异常信息是：" + cause);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息的投递状态是");
    }
}
