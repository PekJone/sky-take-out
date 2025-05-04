package com.sky.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-02  11:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String name;

    private Long categoryId;

    private BigDecimal price;

    private String image ;

    private String description;

    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //接收进来的
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") //响应出去  控制日期响应的格式
    private LocalDateTime createTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
