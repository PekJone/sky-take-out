package com.sky.service;

import com.sky.dto.DishDto;
import com.sky.dto.DishPageQueryDto;
import common.sky.result.PageResult;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:03
 */
public interface DishService {
    void addDish(DishDto dishDto);

    PageResult page(DishPageQueryDto dto);
}
