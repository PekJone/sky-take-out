package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-03  15:46
 */
@Data
public class DishPageQueryDto implements Serializable {
    private int page;
    private int pageSize;

    private String name;

    private Integer categoryId;

    private Integer status;
}
