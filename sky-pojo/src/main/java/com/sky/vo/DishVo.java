package com.sky.vo;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import com.sky.entity.DishFlavor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-03  15:49
 */
public class DishVo {
    private Long id;

    private String name;

    private Long categoryId;

    private BigDecimal price;

    private String image ;

    private String description;

    private Integer status;

    private LocalDateTime updateTime;
    private List<DishFlavor> flavors ;
}
