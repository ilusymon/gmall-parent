package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.service.AttrService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className AttrServiceImpl
 * @date 2021/1/22 19:00
 */
@Service
public class AttrServiceImpl implements AttrService {

    @Resource
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Resource
    private BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        List<BaseAttrInfo> baseAttrInfoList;
        if (category3Id != 0) {
            baseAttrInfoList = baseAttrInfoMapper.selectList(new QueryWrapper<BaseAttrInfo>().eq("category_id", category3Id).eq("category_level", 3));
        } else if (category2Id != 0) {
            baseAttrInfoList = baseAttrInfoMapper.selectList(new QueryWrapper<BaseAttrInfo>().eq("category_id", category2Id).eq("category_level", 2));
        } else {
            baseAttrInfoList = baseAttrInfoMapper.selectList(new QueryWrapper<BaseAttrInfo>().eq("category_id", category1Id).eq("category_level", 1));
        }
        for (BaseAttrInfo baseAttrInfo : baseAttrInfoList) {
            List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.selectList(new QueryWrapper<BaseAttrValue>().eq("attr_id", baseAttrInfo.getId()));
            baseAttrInfo.setAttrValueList(baseAttrValueList);
        }
        return baseAttrInfoList;
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        Long attrInfoId = baseAttrInfo.getId();
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (attrInfoId == null || attrInfoId == 0) {
            //添加
            baseAttrInfoMapper.insert(baseAttrInfo);
        } else {
            //修改(先删再加)
            baseAttrValueMapper.delete(new QueryWrapper<BaseAttrValue>().eq("attr_id", attrInfoId));
        }
        if (attrValueList != null && attrValueList.size() > 0) {
            for (BaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {
        return baseAttrValueMapper.selectList(new QueryWrapper<BaseAttrValue>().eq("attr_id", attrId));
    }

    @Override
    public List<SearchAttr> getSearchAttr(Long skuId) {
        return baseAttrValueMapper.selectSearchAttr(skuId);
    }
}
