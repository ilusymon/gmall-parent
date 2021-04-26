package com.atguigu.gmall.service;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className BaseTrademarkService
 * @date 2021/1/22 21:12
 */
public interface BaseTrademarkService {
    List<BaseTrademark> getTrademarkList();

    IPage<BaseTrademark> getTrademarkPage(Long page, Long limit);

    BaseTrademark getBaseTrademarkById(Long tmId);
}
