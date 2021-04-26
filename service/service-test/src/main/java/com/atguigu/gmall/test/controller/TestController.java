package com.atguigu.gmall.test.controller;

import com.atguigu.gmall.result.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Symon
 * @version 1.0
 * @className TestController
 * @date 2021/1/27 20:37
 */
@RestController
public class TestController {

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("getStock")
    public Result getStock() {
        Integer stock = 0;
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", 1);
        if (lock) {
            stock = (Integer) redisTemplate.opsForValue().get("stock");
            stock--;
            System.out.println("剩余库存数量：" + stock);
            redisTemplate.opsForValue().set("stock", stock);
            redisTemplate.delete("lock");
        }

        return Result.ok(stock);
    }

}
