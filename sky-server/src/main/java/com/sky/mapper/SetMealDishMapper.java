package com.sky.mapper;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-04  15:33
 */
public interface SetMealDishMapper {
    Integer countByDishId(List<Long> ids);
}
