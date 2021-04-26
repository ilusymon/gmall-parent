package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.BaseTrademarkMapper;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author Symon
 * @version 1.0
 * @className BaseTrademarkServiceImpl
 * @date 2021/1/22 21:12
 */
@Service
public class BaseTrademarkServiceImpl implements BaseTrademarkService {

    @Resource
    private BaseTrademarkMapper baseTrademarkMapper;

    @Override
    public List<BaseTrademark> getTrademarkList() {
        return baseTrademarkMapper.selectList(null);
    }

    @Override
    public IPage<BaseTrademark> getTrademarkPage(Long page, Long limit) {
        return baseTrademarkMapper.selectPage(new Page<>(page, limit), null);
    }

    @Override
    public BaseTrademark getBaseTrademarkById(Long tmId) {
        return baseTrademarkMapper.selectById(tmId);
    }
}
