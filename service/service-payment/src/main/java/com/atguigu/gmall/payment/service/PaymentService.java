package com.atguigu.gmall.payment.service;

import com.atguigu.gmall.model.payment.PaymentInfo;

/**
 * @author Symon
 * @version 1.0
 * @className PaymentService
 * @date 2021/3/1 16:30
 */
public interface PaymentService {
    String alipayTradePagePay(Long orderId);

    void updatePayment(PaymentInfo paymentInfo);
}
