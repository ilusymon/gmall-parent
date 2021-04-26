package com.atguigu.gmall.mq.controller;

import com.atguigu.gmall.mq.service.RabbitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Symon
 * @version 1.0
 * @className MqTestController
 * @date 2021/2/26 18:45
 */
@RestController
public class MqTestController {

    @Resource
    RabbitService rabbitService;

    @RequestMapping("testMq")
    public String testMq() {
        rabbitService.sendMessage("a", "b", "c");
        return "testMq";
    }
}
