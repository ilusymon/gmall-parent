package com.atguigu.gmall.product.client;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className ProductFeignClient
 * @date 2021/1/26 18:39
 */
@FeignClient(value = "service-product")
public interface ProductFeignClient {
    @RequestMapping("api/product/getSkuInfoById/{skuId}")
    SkuInfo getSkuInfoById(@PathVariable("skuId") Long skuId);

    @RequestMapping("api/product/getCategoryView/{category3Id}")
    BaseCategoryView getCategoryView(@PathVariable("category3Id") Long category3Id);

    @RequestMapping("api/product/getSkuPriceById/{skuId}")
    BigDecimal getSkuPriceById(@PathVariable("skuId") Long skuId);

    @RequestMapping("api/product/selectSpuSaleAttrListCheckBySku/{spuId}/{skuId}")
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(@PathVariable("spuId") Long spuId, @PathVariable("skuId") Long skuId);

    @RequestMapping("api/product/getValuesSkuJson/{spuId}")
    List<Map<String, Object>> getValuesSkuJson(@PathVariable("spuId") Long spuId);

    @RequestMapping("api/product/getBaseCategoryList")
    List<JSONObject> getBaseCategoryList();

    @RequestMapping("api/product/getBaseTrademarkById/{tmId}")
    BaseTrademark getBaseTrademarkById(@PathVariable("tmId") Long tmId);

    @RequestMapping("api/product/getSearchAttr/{skuId}")
    List<SearchAttr> getSearchAttr(@PathVariable("skuId") Long skuId);
}
