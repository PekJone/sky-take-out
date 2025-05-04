package com.sky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-02  9:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPageQueryDto {
    private String name ;

    private int page;

    private int pageSize;

    private int type;

}
