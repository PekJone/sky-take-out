package com.sky.mapper;

import com.sky.entity.DishFlavor;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-02  11:18
 */
public interface DishFlavorMapper {

    void addBatch(List<DishFlavor> dishFlavors);

    void deleteBatch(List<Long> ids);

    List<DishFlavor> selectByDishId(Long id);
}
