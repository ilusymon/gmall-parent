package com.atguigu.gmall.item.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className ItemFeignClient
 * @date 2021/1/26 18:24
 */
@FeignClient(value = "service-item")
public interface ItemFeignClient {
    @RequestMapping("api/item/{skuId}")
    Map<String,Object> getItem(@PathVariable("skuId") Long skuId);
}
