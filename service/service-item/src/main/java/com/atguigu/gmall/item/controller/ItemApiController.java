package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.item.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className ItemApiController
 * @date 2021/1/26 18:34
 */
@RestController
@RequestMapping("api/item")
public class ItemApiController {

    @Resource
    private ItemService itemService;

    @GetMapping("{skuId}")
    Map<String,Object> getItem(@PathVariable Long skuId) {
        return itemService.getItem(skuId);
    }

}
