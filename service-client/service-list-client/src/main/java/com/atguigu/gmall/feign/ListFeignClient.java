package com.atguigu.gmall.feign;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className ListFeignClient
 * @date 2021/2/1 15:45
 */
@FeignClient(value = "service-list")
public interface ListFeignClient {
    @RequestMapping("api/list/index")
    List<JSONObject> getBaseCategoryList();

    @RequestMapping("api/list/hotScore/{skuId}")
    void hotScore(@PathVariable("skuId") Long skuId);

    @RequestMapping("api/list/onSale/{skuId}")
    void onSale(@PathVariable("skuId") Long skuId);

    @RequestMapping("api/list/cancelSale/{skuId}")
    void cancelSale(@PathVariable("skuId") Long skuId);

    @RequestMapping("api/list/list")
    SearchResponseVo list(@RequestBody SearchParam searchParam);
}
