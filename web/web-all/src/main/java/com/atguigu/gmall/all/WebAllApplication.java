package com.atguigu.gmall.all;

import com.atguigu.gamll.order.client.OrderFeignClient;
import com.atguigu.gmall.cart.client.CartFeignClient;
import com.atguigu.gmall.feign.ListFeignClient;
import com.atguigu.gmall.item.client.ItemFeignClient;
import com.atguigu.gmall.product.client.ProductFeignClient;
import com.atguigu.gmall.user.client.UserFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author Symon
 * @version 1.0
 * @className WebAllApplication
 * @date 2021/1/26 18:19
 */
@SpringBootApplication
@ComponentScan({"com.atguigu.gmall"})
//(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient
@EnableFeignClients(clients = {OrderFeignClient.class, ProductFeignClient.class, UserFeignClient.class, ListFeignClient.class, ItemFeignClient.class, CartFeignClient.class})
public class WebAllApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAllApplication.class, args);
    }
}
