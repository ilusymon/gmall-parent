package com.atguigu.gmall.all.controller;

import com.atguigu.gmall.item.client.ItemFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className ItemController
 * @date 2021/1/26 18:17
 */
@Controller
public class ItemController {

    @Resource
    ItemFeignClient itemFeignClient;

    @RequestMapping("{skuId}.html")
    public String getItem(@PathVariable("skuId") Long skuId, Model model, HttpServletRequest request, HttpSession session){

        // 调用后端服务，查询sku数据
        // sku信息，图片信息，销售属性列表，分类信息，价格信息
        Map<String,Object> map = itemFeignClient.getItem(skuId);

        Map<String,Object> skuInfo = (Map<String, Object>) map.get("skuInfo");
        Integer spuId = (Integer) skuInfo.get("spuId");
        String valuesSkuJson = (String) map.get("valuesSkuJson");

        File file = new File("d:/spu_" + spuId + ".json");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(valuesSkuJson.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAllAttributes(map);
        return "item/index";
    }

    @RequestMapping("test")
    public String test(Model model, HttpServletRequest request, HttpSession session){

        model.addAttribute("hello","hello thymeleaf !");

        request.setAttribute("requestValue","hello request");
        session.setAttribute("sessionValue","hello session");

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add("元素"+i);
        }

        model.addAttribute("list",list);

        model.addAttribute("obj","再见");
        return "test";
    }
}
