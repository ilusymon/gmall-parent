package com.atguigu.gmall.order.service;

import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.payment.PaymentInfo;

import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className OrderService
 * @date 2021/2/25 14:19
 */

public interface OrderService {

    String genTradeNo(String userId);

    boolean checkTradeNo(String userId, String tradeNo);

    String save(OrderInfo order);

    OrderInfo getOrderById(Long orderId);

    void updateOrderInfo(Map<String, Object> map);
}
