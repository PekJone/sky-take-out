package com.we.chat.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @description 白名单配置类
 * @date 2026-04-08  17:26
 */
@Component
@Data
@ConfigurationProperties(prefix = "security.whitelist")
public class WhiteListProperties {
    private List<String> prefix = new ArrayList<>();
    private List<String> exact = new ArrayList<>();
    private List<String> contain = new ArrayList<>();
}
