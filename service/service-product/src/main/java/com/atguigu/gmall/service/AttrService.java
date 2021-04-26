package com.atguigu.gmall.service;

import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;

import java.util.List;

public interface AttrService {

    List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValueList(Long attrId);

    List<SearchAttr> getSearchAttr(Long skuId);
}
