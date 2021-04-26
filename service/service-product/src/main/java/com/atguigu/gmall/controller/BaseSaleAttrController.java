package com.atguigu.gmall.controller;

import com.atguigu.gmall.model.product.BaseSaleAttr;
import com.atguigu.gmall.result.Result;
import com.atguigu.gmall.service.BaseSaleAttrService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className BaseSaleAttrController
 * @date 2021/1/22 21:01
 */
@RestController
@RequestMapping("/admin/product/")
@CrossOrigin
public class BaseSaleAttrController {

    @Resource
    private BaseSaleAttrService baseSaleAttrService;

    @GetMapping("baseSaleAttrList")
    public Result<List<BaseSaleAttr>> baseSaleAttrList() {
        List<BaseSaleAttr> baseSaleAttrList = baseSaleAttrService.baseSaleAttrList();
        return Result.ok(baseSaleAttrList);
    }
}
