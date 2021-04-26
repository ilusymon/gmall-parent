package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.service.FileService;
import jodd.util.StringUtil;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Symon
 * @version 1.0
 * @className FileServiceImpl
 * @date 2021/1/25 11:47
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String fileUpload(MultipartFile file) {
        String path = FileServiceImpl.class.getClassLoader().getResource("Tracker.conf").getPath();
        String[] jpgs = new String[0];
        try {
            ClientGlobal.init(path);
            // 获得tracker连接
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer connection = trackerClient.getConnection();
            // 通过tracker获得storage
            StorageClient storageClient = new StorageClient(connection, null);
            // 上传文件
            String originalFilename = file.getOriginalFilename();
            jpgs = storageClient.upload_file(file.getBytes(), StringUtils.getFilenameExtension(originalFilename), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回url
        StringBuilder url = new StringBuilder("http://192.168.200.128:8080");
        for (String jpg : jpgs) {
            // System.out.println(jpg);
            url.append("/").append(jpg);
        }
        return url.toString();
    }
}
