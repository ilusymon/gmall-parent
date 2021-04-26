package com.atguigu.gmall.all.controller;

import com.atguigu.gamll.order.client.OrderFeignClient;
import com.atguigu.gmall.model.order.OrderInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class PayController {

    @Resource
    private OrderFeignClient orderFeignClient;

    @RequestMapping("pay.html")
    public String index(Long orderId,Model model){

        // 跳转到支付方式选择界面
        OrderInfo orderInfo = orderFeignClient.getOrderById(orderId);
        model.addAttribute("orderInfo",orderInfo);
        return "payment/pay";
    }

}