package com.atguigu.gmall.controller;

import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.result.Result;
import com.atguigu.gmall.service.SpuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className SpuController
 * @date 2021/1/22 20:18
 */
@RestController
@RequestMapping("/admin/product/")
@CrossOrigin
public class SpuController {

    @Resource
    private SpuService spuService;

    @GetMapping("{page}/{limit}")
    public Result<IPage<SpuInfo>> getSpuInfoPage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, Long category3Id) {
        IPage<SpuInfo> ipage = spuService.getSpuInfoPage(page, limit, category3Id);
        return Result.ok(ipage);
    }

    @PostMapping("saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo) {
        spuService.saveSpuInfo(spuInfo);
        return Result.ok();
    }

    @GetMapping("spuSaleAttrList/{spuId}")
    public Result<List<SpuSaleAttr>> spuSaleAttrList(@PathVariable("spuId") Long spuId) {
        List<SpuSaleAttr> spuSaleAttrList = spuService.spuSaleAttrList(spuId);
        return Result.ok(spuSaleAttrList);
    }

    @GetMapping("spuImageList/{spuId}")
    public Result<List<SpuImage>> spuImageList(@PathVariable("spuId") Long spuId) {
        List<SpuImage> spuImageList = spuService.spuImageList(spuId);
        return Result.ok(spuImageList);
    }
}
