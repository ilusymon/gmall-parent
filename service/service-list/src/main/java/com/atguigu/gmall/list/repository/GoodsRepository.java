package com.atguigu.gmall.list.repository;

import com.atguigu.gmall.model.list.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Symon
 * @version 1.0
 * @className GoodsRespository
 * @date 2021/2/19 20:53
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
