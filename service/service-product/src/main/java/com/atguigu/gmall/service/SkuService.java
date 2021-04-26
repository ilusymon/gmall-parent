package com.atguigu.gmall.service;

import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.math.BigDecimal;

public interface SkuService {
    IPage<SkuInfo> list(Long pageNum, Long pageSize);

    void saveSkuInfo(SkuInfo skuInfo);

    void onSale(Long skuId);

    void cancelSale(Long skuId);

    SkuInfo getSkuInfoById(Long skuId);

    BigDecimal getSkuPriceById(Long skuId);

}
