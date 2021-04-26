package com.atguigu.gmall.list.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;

import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className ListService
 * @date 2021/2/1 15:55
 */
public interface ListService {
    List<JSONObject> getBaseCategoryList();

    void hotScore(Long skuId);

    void onSale(Long skuId);

    void cancelSale(Long skuId);

    SearchResponseVo list(SearchParam searchParam);
}
