package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  16:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishFlavor {
    private Long dishId;

    private Long id;

    private String name ;

    private String value;
}
