package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  11:12
 */
@Configuration
@ConfigurationProperties(prefix = "sky.alioss")
@Data
public class AliOssProperties {
    private String endpoint;
    private String assessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
