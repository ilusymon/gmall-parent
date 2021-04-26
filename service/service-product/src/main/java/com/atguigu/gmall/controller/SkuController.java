package com.atguigu.gmall.controller;

import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.result.Result;
import com.atguigu.gmall.service.SkuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Symon
 * @version 1.0
 * @className SkuController
 * @date 2021/1/25 14:16
 */
@RestController
@RequestMapping("/admin/product/")
@CrossOrigin
public class SkuController {

    @Resource
    private SkuService skuService;

    @GetMapping("list/{pageNum}/{pageSize}")
    public Result<IPage<SkuInfo>> list(@PathVariable Long pageNum, @PathVariable Long pageSize) {
        IPage<SkuInfo> skuInfoList = skuService.list(pageNum, pageSize);
        return Result.ok(skuInfoList);
    }

    @PostMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo) {
        skuService.saveSkuInfo(skuInfo);
        return Result.ok();
    }

    @GetMapping("onSale/{skuId}")
    public Result onSale(@PathVariable Long skuId) {
        skuService.onSale(skuId);
        return Result.ok();
    }

    @GetMapping("cancelSale/{skuId}")
    public Result cancelSale(@PathVariable Long skuId) {
        skuService.cancelSale(skuId);
        return Result.ok();
    }
}
