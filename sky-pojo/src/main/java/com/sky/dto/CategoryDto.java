package com.sky.dto;

import lombok.Data;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:20
 */
@Data
public class CategoryDto {
    private Long id;
    private String name;
    private int type;
    private int sort;
}
