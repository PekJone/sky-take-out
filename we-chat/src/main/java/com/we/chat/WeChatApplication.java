package com.we.chat;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.swing.*;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  9:30
 */
@MapperScan("com.we.chat.mapper")
@SpringBootApplication
@EnableTransactionManagement
@Slf4j
@ServletComponentScan //扫描Servlet组件（如Filter、Servlet、Listener等） 必须加，否则 @WebFilter 不生效
public class WeChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeChatApplication.class,args);
            log.info("WeChat server start:端口8090");
    }
}
