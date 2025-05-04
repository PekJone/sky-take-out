package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import common.sky.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  15:04
 *
 */
@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("初始化aliossutil对象");
        log.info(aliOssProperties.toString());
        BaseContext.setCurrentId(2L);
        log.info("初始化baseContext={}",BaseContext.getCurrentId());
        AliOssUtil aliOssUtil = new AliOssUtil(aliOssProperties.getEndpoint(),aliOssProperties.getAssessKeyId(),aliOssProperties.getAccessKeySecret(),aliOssProperties.getBucketName());
        return aliOssUtil;
    }

}
