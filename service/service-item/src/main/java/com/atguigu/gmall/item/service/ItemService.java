package com.atguigu.gmall.item.service;

import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className ItemService
 * @date 2021/1/26 18:36
 */
public interface ItemService {
    Map<String, Object> getItem(Long skuId);
}
