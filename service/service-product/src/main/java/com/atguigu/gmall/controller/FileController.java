package com.atguigu.gmall.controller;


import com.atguigu.gmall.result.Result;
import com.atguigu.gmall.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/admin/product/")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file) {
        String url = fileService.fileUpload(file);

        return Result.ok(url);
    }
}
