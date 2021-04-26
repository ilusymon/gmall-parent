package com.atguigu.gmall.list.controller;

import com.atguigu.gmall.result.Result;
import com.sun.org.apache.regexp.internal.RE;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Symon
 * @version 1.0
 * @className ListTestController
 * @date 2021/2/22 8:55
 * 测试程序
 */
@RequestMapping("list/test")
@RestController
public class ListTestController {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @RequestMapping("search")
    public Result test() {
        try {
            // dsl封装
            SearchRequest searchRequest = new SearchRequest().types("info").indices("goods");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("category3Id", "13");
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", "黑色");
            boolQueryBuilder.filter(termQueryBuilder).must(matchQueryBuilder);
            searchSourceBuilder.from(0).size(60).query(boolQueryBuilder);

            String dsl = searchSourceBuilder.toString();
            System.out.println(dsl);

            searchRequest.source(searchSourceBuilder);


            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = search.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }
}
