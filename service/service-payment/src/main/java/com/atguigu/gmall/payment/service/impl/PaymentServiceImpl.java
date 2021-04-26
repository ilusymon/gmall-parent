package com.atguigu.gmall.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.atguigu.gamll.order.client.OrderFeignClient;
import com.atguigu.gmall.model.enums.PaymentStatus;
import com.atguigu.gmall.model.enums.PaymentType;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.payment.PaymentInfo;
import com.atguigu.gmall.mq.constant.MqConst;
import com.atguigu.gmall.mq.service.RabbitService;
import com.atguigu.gmall.payment.config.AlipayConfig;
import com.atguigu.gmall.payment.mapper.PaymentInfoMapper;
import com.atguigu.gmall.payment.service.PaymentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className PaymentServiceImpl
 * @date 2021/3/1 16:31
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private OrderFeignClient orderFeignClient;

    @Resource
    private PaymentInfoMapper paymentInfoMapper;

    @Resource
    private RabbitService rabbitService;

    @Override
    public String alipayTradePagePay(Long orderId) {
        // 公共参数
        // AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2021001163617452","","json","GBK","","RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(AlipayConfig.notify_payment_url);
        request.setReturnUrl(AlipayConfig.return_payment_url);

        OrderInfo orderInfo = orderFeignClient.getOrderById(orderId);
        // 请求参数
        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no", orderInfo.getOutTradeNo());
        map.put("product_code", "FAST_INSTANT_TRADE_PAY");
        map.put("total_amount", 0.01);
        map.put("subject", orderInfo.getOrderDetailList().get(0).getSkuName());
        request.setBizContent(JSON.toJSONString(map));
        /*request.setBizContent("{" +
                "\"out_trade_no\":\"20150320010101001\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "\"total_amount\":88.88," +
                "\"subject\":\"Iphone6 16G\"," +
                "  }");*/
        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

        // 保存支付信息
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
        paymentInfo.setPaymentStatus(PaymentStatus.UNPAID.toString());
        paymentInfo.setSubject(orderInfo.getOrderDetailList().get(0).getSkuName());
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setTotalAmount(orderInfo.getTotalAmount());
        paymentInfo.setOrderId(orderId);
        paymentInfo.setPaymentType(PaymentType.ALIPAY.getComment());
        paymentInfoMapper.insert(paymentInfo);

        return response.getBody();
    }

    @Override
    public void updatePayment(PaymentInfo paymentInfo) {
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", paymentInfo.getOutTradeNo());

        paymentInfo = paymentInfoMapper.selectOne(wrapper);

        // 发送支付成功通知，由订单系统消费，订单状态修改为已支付
        Map<String, Object> message = new HashMap<>();
        message.put("out_trade_no", paymentInfo.getOutTradeNo());
        message.put("order_id", paymentInfo.getOrderId());
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_PAYMENT_PAY, MqConst.ROUTING_PAYMENT_PAY, JSON.toJSONString(message));
        //根据回调的out_trade_no修改支付状态
        paymentInfoMapper.update(paymentInfo, wrapper);
    }

}
