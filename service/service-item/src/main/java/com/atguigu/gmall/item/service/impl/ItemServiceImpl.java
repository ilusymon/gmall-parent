package com.atguigu.gmall.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.feign.ListFeignClient;
import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Symon
 * @version 1.0
 * @className ItemServiceImpl
 * @date 2021/1/26 18:36
 */

/*
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ProductFeignClient productFeignClient;

    @Resource
    private ListFeignClient listFeignClient;

    /*
     *
     *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
     * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
     * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
     * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
     * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
     *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
     *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
     *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
     *           ░     ░ ░      ░  ░
     */
    @Override
    public Map<String, Object> getItem(Long skuId) {
        Map<String, Object> result = new HashMap<>();
        

        // skuInfo // image
        SkuInfo skuInfo = productFeignClient.getSkuInfoById(skuId);
        result.put("skuInfo",skuInfo);

        // 销售属性列表
        List<SpuSaleAttr> spuSaleAttrList = productFeignClient.selectSpuSaleAttrListCheckBySku(skuInfo.getSpuId(), skuId);
        result.put("spuSaleAttrList",spuSaleAttrList);
        // 分类信息
        BaseCategoryView baseCategoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
        result.put("categoryView", baseCategoryView);
        // 价格
        BigDecimal price = productFeignClient.getSkuPriceById(skuId);
        result.put("price", price);

        List<Map<String, Object>> valuesSkuMaps = productFeignClient.getValuesSkuJson(skuInfo.getSpuId());
        Map<String,Object> map = new HashMap<>();
        for (Map<String, Object> valuesSkuMap : valuesSkuMaps) {
            map.put((String)valuesSkuMap.get("valueIds"),valuesSkuMap.get("sku_id"));
        }
        result.put("valuesSkuJson", JSON.toJSONString(map));

        listFeignClient.hotScore(skuId);
        return result;
    }
}
