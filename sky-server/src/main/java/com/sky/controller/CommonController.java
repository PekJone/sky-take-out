package com.sky.controller;

import common.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  15:11
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {

    private AliOssUtil aliOssUtil;

    @Autowired
    public void setAliOssUtil(AliOssUtil aliOssUtil) {
        this.aliOssUtil = aliOssUtil;
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {
        String originFileName = file.getOriginalFilename();
        String lastName = originFileName.substring(originFileName.lastIndexOf("."));
        log.info("原始文件名{}",lastName);
        String objectName = UUID.randomUUID().toString()+lastName;
         String url = aliOssUtil.upload(file.getBytes(),objectName);

         return Result.success(url);
    }
}
