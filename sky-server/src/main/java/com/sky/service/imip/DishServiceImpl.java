package com.sky.service.imip;

import com.sky.mapper.DishMapper;
import com.sky.service.DishService;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:03
 */
public class DishServiceImpl implements DishService {
    private DishMapper dishMapper;

    public void setDishMapper(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }
}
