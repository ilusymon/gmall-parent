package com.atguigu.gmall.controller;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.result.Result;
import com.atguigu.gmall.service.AttrService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className AttrController
 * @date 2021/1/22 18:58
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/product/")
public class AttrController {

    @Resource
    private AttrService attrService;

    @GetMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result<List<BaseAttrInfo>> attrInfoList(
            @PathVariable("category1Id") Long category1Id,
            @PathVariable("category2Id") Long category2Id,
            @PathVariable("category3Id") Long category3Id) {
        List<BaseAttrInfo> baseAttrInfoList = attrService.attrInfoList(category1Id, category2Id, category3Id);
        return Result.ok(baseAttrInfoList);
    }

    /*
    * @author Symon
    * @description 添加或者修改
    * @date 2021/1/22 19:54
    **/
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo) {
        attrService.saveAttrInfo(baseAttrInfo);
        return Result.ok();

    }

    @GetMapping("getAttrValueList/{attrId}")
    public Result<List<BaseAttrValue>> getAttrValueList(@PathVariable("attrId") Long attrId) {
        List<BaseAttrValue> attrValueList = attrService.getAttrValueList(attrId);
        return Result.ok(attrValueList);
    }
}
