package com.atguigu.gmall.list.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.list.service.ListService;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className ListApiController
 * @date 2021/2/1 15:54
 */
@RestController
@RequestMapping("api/list")
public class ListApiController {

    @Resource
    private ListService listService;

    @RequestMapping("index")
    List<JSONObject> getBaseCategoryList() {
        return listService.getBaseCategoryList();
    }

    @RequestMapping("hotScore/{skuId}")
    void hotScore(@PathVariable("skuId") Long skuId) {
        listService.hotScore(skuId);
    }


    @RequestMapping("onSale/{skuId}")
    void onSale(@PathVariable("skuId") Long skuId) {
        listService.onSale(skuId);
    }

    @RequestMapping("cancelSale/{skuId}")
    void cancelSale(@PathVariable("skuId") Long skuId) {
        listService.cancelSale(skuId);
    }

    @RequestMapping("list")
    SearchResponseVo list(@RequestBody SearchParam searchParam) {
        return listService.list(searchParam);
    }
}
