package com.atguigu.gmall.service;


import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface SpuService {

    IPage<SpuInfo> getSpuInfoPage(Integer page, Integer limit, Long category3Id);

    void saveSpuInfo(SpuInfo spuInfo);

    List<SpuSaleAttr> spuSaleAttrList(Long spuId);

    List<SpuImage> spuImageList(Long spuId);

    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(Long spuId, Long skuId);

    List<Map<String, Object>> getValuesSkuJson(Long spuId);
}
