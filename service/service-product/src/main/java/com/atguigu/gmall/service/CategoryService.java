package com.atguigu.gmall.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.product.*;

import java.util.List;

public interface CategoryService {
    List<BaseCategory1> getCategory1();

    List<BaseCategory2> getCategory2(Long category1Id);

    List<BaseCategory3> getCategory3(Long category2Id);

    BaseCategoryView getCategoryView(Long category3Id);

    List<JSONObject> getBaseCategoryList();
}
