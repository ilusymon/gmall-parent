package com.atguigu.gmall.mapper;

import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className BaseAttrValueMapper
 * @date 2021/1/22 15:15
 */
@Mapper
public interface BaseAttrValueMapper extends BaseMapper<BaseAttrValue> {
    List<SearchAttr> selectSearchAttr(Long skuId);
}
