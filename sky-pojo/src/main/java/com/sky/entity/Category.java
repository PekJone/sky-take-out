package com.sky.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-02  9:30
 */
@Data
public class Category {
    private Long id;
    private String name;
    private int type;
    private int sort;
    private int status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //接收进来的
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") //响应出去  控制日期响应的格式
    private LocalDateTime createTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
