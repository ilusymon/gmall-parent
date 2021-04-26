package com.atguigu.gmall.mq.service.impl;

import com.atguigu.gmall.mq.service.RabbitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Symon
 * @version 1.0
 * @className RabbitServiceImpl
 * @date 2021/2/26 19:14
 */
@Service
public class RabbitServiceImpl implements RabbitService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String a, String b, String c) {
        rabbitTemplate.convertAndSend(a, b, c);
    }
}
