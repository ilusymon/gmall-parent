package com.atguigu.gmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Symon
 * @version 1.0
 * @className FileService
 * @date 2021/1/25 11:46
 */
public interface FileService {
    String fileUpload(MultipartFile file);
}
