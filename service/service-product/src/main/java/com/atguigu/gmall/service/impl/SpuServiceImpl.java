package com.atguigu.gmall.service.impl;


import com.atguigu.gmall.mapper.SpuImageMapper;
import com.atguigu.gmall.mapper.SpuMapper;
import com.atguigu.gmall.mapper.SpuSaleAttrMapper;
import com.atguigu.gmall.mapper.SpuSaleAttrValueMapper;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.product.SpuSaleAttrValue;
import com.atguigu.gmall.service.SpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className AttrServiceImpl
 * @date 2021/1/22 19:00
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Resource
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Resource
    private SpuImageMapper spuImageMapper;

    @Override
    public IPage<SpuInfo> getSpuInfoPage(Integer page, Integer limit, Long category3Id) {
        Page<SpuInfo> spuInfoPage = new Page<>();
        spuInfoPage.setSize(limit).setCurrent(page);
        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", category3Id);
        return spuMapper.selectPage(spuInfoPage, wrapper);
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        spuMapper.insert(spuInfo);
        Long spuId = spuInfo.getId();
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            spuSaleAttr.setSpuId(spuId);
            spuSaleAttrMapper.insert(spuSaleAttr);
            String saleAttrName = spuSaleAttr.getSaleAttrName();
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValue.setSpuId(spuId);
                spuSaleAttrValue.setSaleAttrName(saleAttrName);
                spuSaleAttrValueMapper.insert(spuSaleAttrValue);
            }
        }
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage spuImage : spuImageList) {
            spuImage.setSpuId(spuId);
            spuImageMapper.insert(spuImage);
        }
    }

    @Override
    public List<SpuSaleAttr> spuSaleAttrList(Long spuId) {
        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrMapper.selectList(new QueryWrapper<SpuSaleAttr>().eq("spu_id", spuId));
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            Long baseSaleAttrId = spuSaleAttr.getBaseSaleAttrId();
            List<SpuSaleAttrValue> saleAttrValueList = spuSaleAttrValueMapper.selectList(new QueryWrapper<SpuSaleAttrValue>().eq("spu_id", spuId).eq("base_sale_attr_id", baseSaleAttrId));
            spuSaleAttr.setSpuSaleAttrValueList(saleAttrValueList);
        }
        return spuSaleAttrList;
    }

    @Override
    public List<SpuImage> spuImageList(Long spuId) {
        return spuImageMapper.selectList(new QueryWrapper<SpuImage>().eq("spu_id", spuId));
    }

    @Override
    public List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(Long spuId, Long skuId) {
        return spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(spuId, skuId);
    }

    @Override
    public List<Map<String, Object>> getValuesSkuJson(Long spuId) {
        return spuSaleAttrMapper.selectValuesBySkuId(spuId);
    }
}
