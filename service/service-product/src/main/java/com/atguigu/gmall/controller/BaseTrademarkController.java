package com.atguigu.gmall.controller;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.result.Result;
import com.atguigu.gmall.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className BaseTrademarkController
 * @date 2021/1/22 21:09
 */
@RestController
@RequestMapping("/admin/product/baseTrademark/")
@CrossOrigin
public class BaseTrademarkController {

    @Resource
    private BaseTrademarkService baseTrademarkService;

    @GetMapping("getTrademarkList")
    public Result<List<BaseTrademark>> getTrademarkList() {
        List<BaseTrademark> trademarkList = baseTrademarkService.getTrademarkList();
        return Result.ok(trademarkList);
    }

    @GetMapping("{page}/{limit}")
    public Result<IPage<BaseTrademark>> getTrademarkPage(@PathVariable Long page, @PathVariable Long limit) {
        IPage<BaseTrademark> trademarkPage = baseTrademarkService.getTrademarkPage(page, limit);
        return Result.ok(trademarkPage);
    }
}
