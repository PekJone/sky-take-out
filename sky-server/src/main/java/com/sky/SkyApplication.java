package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-27  15:24
 */
@MapperScan("com.sky.mapper")
@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class SkyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class,args);
        log.info("server start");
    }
}
