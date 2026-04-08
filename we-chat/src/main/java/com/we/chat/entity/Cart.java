package com.we.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  15:46
 */
@Data
@TableName("cart")
public class Cart {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer num;
}