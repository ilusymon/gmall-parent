package com.atguigu.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Symon
 * @version 1.0
 * @className ProductApplication
 * @date 2021/1/22 8:30
 */
@SpringBootApplication
@ComponentScan("com.atguigu")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.atguigu.gmall"})
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
