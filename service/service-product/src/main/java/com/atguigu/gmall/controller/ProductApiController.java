package com.atguigu.gmall.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.service.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/product/")
public class ProductApiController {


    @Resource
    SkuService skuService;

    @Resource
    SpuService spuService;

    @Resource
    CategoryService categoryService;

    @Resource
    private AttrService attrService;

    @Resource
    private BaseTrademarkService baseTrademarkService;

    @RequestMapping("getSkuInfoById/{skuId}")
    SkuInfo getSkuInfoById(@PathVariable("skuId") Long skuId){

        SkuInfo skuInfo = skuService.getSkuInfoById(skuId);

        return  skuInfo;
    }

    @RequestMapping("getCategoryView/{category3Id}")
    BaseCategoryView getCategoryView(@PathVariable("category3Id") Long category3Id) {
        return  categoryService.getCategoryView(category3Id);
    }

    @RequestMapping("getSkuPriceById/{skuId}")
    BigDecimal getSkuPriceById(@PathVariable("skuId") Long skuId) {
        return  skuService.getSkuPriceById(skuId);
    }

    @RequestMapping("selectSpuSaleAttrListCheckBySku/{spuId}/{skuId}")
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(
            @PathVariable("spuId") Long spuId,
            @PathVariable("skuId") Long skuId) {
        return spuService.selectSpuSaleAttrListCheckBySku(spuId, skuId);
    }

    @RequestMapping("getValuesSkuJson/{spuId}")
    List<Map<String, Object>> getValuesSkuJson(@PathVariable("spuId") Long spuId) {
        return spuService.getValuesSkuJson(spuId);
    }

    @RequestMapping("getBaseCategoryList")
    List<JSONObject> getBaseCategoryList() {
        return categoryService.getBaseCategoryList();
    }

    @RequestMapping("getBaseTrademarkById/{tmId}")
    BaseTrademark getBaseTrademarkById(@PathVariable("tmId") Long tmId) {
        return baseTrademarkService.getBaseTrademarkById(tmId);
    }

    @RequestMapping("getSearchAttr/{skuId}")
    List<SearchAttr> getSearchAttr(@PathVariable("skuId") Long skuId) {
        return attrService.getSearchAttr(skuId);
    }
}