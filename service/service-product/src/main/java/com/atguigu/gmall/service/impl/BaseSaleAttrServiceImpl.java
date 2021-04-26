package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.BaseSaleAttrMapper;
import com.atguigu.gmall.model.product.BaseSaleAttr;
import com.atguigu.gmall.service.BaseSaleAttrService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className BaseSaleAttrServiceImpl
 * @date 2021/1/22 21:04
 */
@Service
public class BaseSaleAttrServiceImpl implements BaseSaleAttrService {

    @Resource
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectList(null);
    }
}
