package com.atguigu.gmall.all.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.feign.ListFeignClient;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className ListController
 * @date 2021/2/1 15:43
 */
@Controller
public class ListController {

    @Resource
    private ListFeignClient listFeignClient;

    @RequestMapping("index")
    public String index(Model model){

        List<JSONObject> jsonObjectList = listFeignClient.getBaseCategoryList();

        model.addAttribute("list",jsonObjectList);
        return "index/index";
    }

    @RequestMapping({"list.html", "search.html"})
    public String list(Model model, SearchParam searchParam) {
        // 页面的渲染数据
        SearchResponseVo searchResponseVo = listFeignClient.list(searchParam);
        model.addAttribute("goodsList", searchResponseVo.getGoodsList());
        model.addAttribute("trademarkList", searchResponseVo.getTrademarkList());
        model.addAttribute("attrsList", searchResponseVo.getAttrsList());
        model.addAttribute("urlParam", "");
        return "list/index";
    }

}
