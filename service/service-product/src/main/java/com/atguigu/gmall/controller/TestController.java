package com.atguigu.gmall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Symon
 * @version 1.0
 * @className TestController
 * @date 2021/1/22 8:36
 */
@RestController
public class TestController {

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
