package com.we.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  15:45
 */
@Data
@TableName("product")
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private String image;
    private String detail;
    private Date createTime;
}
