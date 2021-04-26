package com.atguigu.gmall.list.controller;

import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.result.Result;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Symon
 * @version 1.0
 * @className EsController
 * @date 2021/2/1 18:49
 */
@RestController
@CrossOrigin
public class EsController {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @RequestMapping("createIndex")
    Result createIndex(){
        elasticsearchRestTemplate.createIndex(Goods.class);
        elasticsearchRestTemplate.putMapping(Goods.class);
        return Result.ok();
    }

}
