package com.atguigu.gmall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.mapper.*;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import feign.QueryMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Symon
 * @version 1.0
 * @className CategoryServiceImpl
 * @date 2021/1/22 11:46
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private Category1Mapper categoryMapper1;

    @Resource
    private Category2Mapper categoryMapper2;

    @Resource
    private Category3Mapper categoryMapper3;

    @Resource
    private BaseCategoryViewMapper baseCategoryViewMapper;


    @Override
    public List<BaseCategory1> getCategory1() {
        return categoryMapper1.selectList(null);
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        return categoryMapper2.selectList(new QueryWrapper<BaseCategory2>().eq("category1_id",category1Id));
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        return categoryMapper3.selectList(new QueryWrapper<BaseCategory3>().eq("category2_id",category2Id));
    }

    @Override
    public BaseCategoryView getCategoryView(Long category3Id) {
        BaseCategory3 baseCategory3 = categoryMapper3.selectById(category3Id);
        BaseCategory2 baseCategory2 = categoryMapper2.selectById(baseCategory3.getCategory2Id());
        BaseCategory1 baseCategory1 = categoryMapper1.selectById(baseCategory2.getCategory1Id());
        BaseCategoryView baseCategoryView = new BaseCategoryView();
        baseCategoryView.setCategory1Id(baseCategory1.getId());
        baseCategoryView.setCategory1Name(baseCategory1.getName());
        baseCategoryView.setCategory2Id(baseCategory2.getId());
        baseCategoryView.setCategory2Name(baseCategory2.getName());
        baseCategoryView.setCategory3Id(baseCategory3.getId());
        baseCategoryView.setCategory3Name(baseCategory3.getName());
        return baseCategoryView;
    }

    @Override
    public List<JSONObject> getBaseCategoryList() {
        // 封装分类结构
        List<BaseCategoryView> categoryViews = baseCategoryViewMapper.selectList(null);

        // json中应该有三个字段分别是：
        // categoryId
        // categoryName
        // categoryChild
        List<JSONObject> category1List = new ArrayList<>();

        Map<Long, List<BaseCategoryView>> c1Map = categoryViews.stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory1Id));

        for (Map.Entry<Long, List<BaseCategoryView>> c1Entry : c1Map.entrySet()) {
            Long c1Id = c1Entry.getKey();
            String c1Name = c1Entry.getValue().get(0).getCategory1Name();
            JSONObject jsonObjectC1 = new JSONObject();
            jsonObjectC1.put("categoryId", c1Id);
            jsonObjectC1.put("categoryName", c1Name);
            // 第二层
            Map<Long, List<BaseCategoryView>> c2Map = c1Entry.getValue().stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory2Id));
            List<JSONObject> category2List = new ArrayList<>();
            for (Map.Entry<Long, List<BaseCategoryView>> c2Entry : c2Map.entrySet()) {
                Long c2Id = c2Entry.getKey();
                String c2Name = c2Entry.getValue().get(0).getCategory2Name();
                JSONObject jsonObjectC2 = new JSONObject();
                jsonObjectC2.put("categoryId", c2Id);
                jsonObjectC2.put("categoryName", c2Name);
                // 第三层三级分类
                Map<Long, List<BaseCategoryView>> c3Map = c1Entry.getValue().stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory3Id));
                List<JSONObject> category3List = new ArrayList<>();
                for (Map.Entry<Long, List<BaseCategoryView>> c3Entry : c3Map.entrySet()) {
                    Long c3Id = c3Entry.getKey();
                    String c3Name = c3Entry.getValue().get(0).getCategory3Name();
                    JSONObject jsonObjectC3 = new JSONObject();
                    jsonObjectC3.put("categoryId", c3Id);
                    jsonObjectC3.put("categoryName", c3Name);
                    category3List.add(jsonObjectC3);
                }
                jsonObjectC2.put("categoryChild", category3List);
                category2List.add(jsonObjectC2);
            }
            jsonObjectC1.put("categoryChild", category2List);
            category1List.add(jsonObjectC1);
        }
        return category1List;
    }

}
