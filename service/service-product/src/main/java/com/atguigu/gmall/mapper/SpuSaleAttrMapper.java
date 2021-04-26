package com.atguigu.gmall.mapper;

import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("spuId") Long spuId, @Param("skuId") Long skuId);

    List<Map<String, Object>> selectValuesBySkuId(@Param("spuId") Long spuId);
}
