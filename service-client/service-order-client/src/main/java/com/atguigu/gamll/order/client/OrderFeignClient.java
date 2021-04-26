package com.atguigu.gamll.order.client;


import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.payment.PaymentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(value = "service-order")
public interface OrderFeignClient {

    @RequestMapping("api/order/genTradeNo")
    String genTradeNo();

    @RequestMapping("api/order/getOrderById/{orderId}")
    OrderInfo getOrderById(@PathVariable("orderId") Long orderId);

}
