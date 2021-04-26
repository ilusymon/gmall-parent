package com.atguigu.gmall.mq.service;

/**
 * @author Symon
 * @version 1.0
 * @className RabbitService
 * @date 2021/2/26 19:13
 */
public interface RabbitService {
    void sendMessage(String a, String b, String c);
}
