package com.atguigu.gmall.payment.controller;

import com.atguigu.gmall.model.enums.PaymentStatus;
import com.atguigu.gmall.model.payment.PaymentInfo;
import com.atguigu.gmall.payment.service.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Symon
 * @version 1.0
 * @className PayApiController
 * @date 2021/3/1 15:56
 */
@RestController
@RequestMapping("api/payment")
public class PayApiController {

    @Resource
    private PaymentService paymentService;


    @RequestMapping("alipay/callback/return")
    public String alipayCallback(HttpServletRequest request) {

        PaymentInfo paymentInfo = new PaymentInfo();

        String trade_no = request.getParameter("trade_no");
        String out_trade_no = request.getParameter("out_trade_no");
        String callback_content = request.getQueryString();

        paymentInfo.setTradeNo(trade_no);
        paymentInfo.setOutTradeNo(out_trade_no);
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setCallbackContent(callback_content);
        paymentInfo.setPaymentStatus(PaymentStatus.PAID.toString());
        // 接收支付宝回调信息，修改支付状态
        paymentService.updatePayment(paymentInfo);

        return "<form action=\"http://payment.gmall.com/payment/success.html\"></form>\n" +
                "<script>document.forms[0].submit();</script>";
    }

    @RequestMapping("alipay/submit/{orderId}")
    public String alipayTradePagePay(@PathVariable("orderId") Long orderId) {
        // 返回一个支付页面
        return paymentService.alipayTradePagePay(orderId);
    }

}
