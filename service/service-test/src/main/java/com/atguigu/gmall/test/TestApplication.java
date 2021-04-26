package com.atguigu.gmall.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Symon
 * @version 1.0
 * @className ProductApplication
 * @date 2021/1/22 8:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.atguigu.gmall")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
