package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.aspect.GmallCache;
import com.atguigu.gmall.feign.ListFeignClient;
import com.atguigu.gmall.mapper.*;
import com.atguigu.gmall.model.product.SkuAttrValue;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SkuSaleAttrValue;
import com.atguigu.gmall.service.SkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Symon
 * @version 1.0
 * @className SkuServiceImpl
 * @date 2021/1/25 14:21
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuInfoMapper skuInfoMapper;
    @Resource
    private SkuImageMapper skuImageMapper;
    @Resource
    private SkuAttrValueMapper skuAttrValueMapper;
    @Resource
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Resource
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ListFeignClient listFeignClient;

    @Override
    public IPage<SkuInfo> list(Long pageNum, Long pageSize) {
        Page<SkuInfo> infoPage = new Page<>(pageNum, pageSize);
        return skuInfoMapper.selectPage(infoPage, null);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        skuInfoMapper.insert(skuInfo);
        Long skuId = skuInfo.getId();
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuId);
            skuImageMapper.insert(skuImage);
        }
        for (SkuAttrValue skuAttrValue : skuInfo.getSkuAttrValueList()) {
            skuAttrValue.setSkuId(skuId);
            skuAttrValueMapper.insert(skuAttrValue);
        }
        for (SkuSaleAttrValue skuSaleAttrValue : skuInfo.getSkuSaleAttrValueList()) {
            skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);
        }
    }

    @Override
    public void onSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(1);
        skuInfoMapper.updateById(skuInfo);

        // 同步搜索引擎
        listFeignClient.onSale(skuId);
    }

    @Override
    public void cancelSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(0);
        skuInfoMapper.updateById(skuInfo);

        // 同步搜索引擎
        listFeignClient.cancelSale(skuId);
    }
    @Override
    @GmallCache
    public SkuInfo getSkuInfoById(Long skuId) {
        SkuInfo skuInfo = null;
        skuInfo = getSkuInfoFromDb(skuId);
        return skuInfo;
    }

    public SkuInfo getSkuInfoByIdBak(Long skuId) {
        SkuInfo skuInfo = null;
        // 查询缓存
        skuInfo = (SkuInfo) redisTemplate.opsForValue().get("sku:" + skuId + ":info");
        if (skuInfo == null) {
            String lockTag = UUID.randomUUID().toString();
            Boolean OK = redisTemplate.opsForValue().setIfAbsent("sku:" + skuId + ":lock", lockTag, 3, TimeUnit.SECONDS);
            if (OK) {// OK为true，说明成功拿到了锁
                // 查询数据库
                skuInfo = getSkuInfoFromDb(skuId);
                if (skuInfo != null) {
                    // 同步缓存
                    redisTemplate.opsForValue().set("sku:" + skuId + ":info", skuInfo);
                } else {
                    redisTemplate.opsForValue().set("sku:" + skuId + ":info", null, 5, TimeUnit.MINUTES);
                }
                // String delTag = (String) redisTemplate.opsForValue().get("sku:" + skuId + ":info");
                // if (delTag.equals(lockTag)) {
                //     // 释放锁
                //     redisTemplate.delete("sku:" + skuId + ":lock");
                // }
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";// 脚本查询是否存在，存在则删除，否则返回0
                // 设置lua脚本返回的数据类型
                DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
                redisScript.setResultType(Long.class);
                redisScript.setScriptText(script);
                redisTemplate.execute(redisScript, Arrays.asList("sku:" + skuId + ":lock"), lockTag);// 执行
            } else {
                //没有查询到缓存数据，并且没有获得分布式锁，自旋
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //此处务必加入return，否则回产生两个线程
                return getSkuInfoById(skuId);
            }
        }
        return skuInfo;
    }

    /*
    * @author Symon
    * @description 查询数据库
    * @date 2021/1/27 19:36
    * @param [skuId]
    * @return com.atguigu.gmall.model.product.SkuInfo
    **/
    private SkuInfo getSkuInfoFromDb(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        QueryWrapper<SkuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id",skuId);
        List<SkuImage> skuImages = skuImageMapper.selectList(queryWrapper);
        skuInfo.setSkuImageList(skuImages);
        return skuInfo;
    }

    @Override
    public BigDecimal getSkuPriceById(Long skuId) {
        return skuInfoMapper.selectById(skuId).getPrice();
    }

}
