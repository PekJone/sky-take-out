package com.sky.service;

import com.sky.dto.DishDto;
import com.sky.dto.DishPageQueryDto;
import com.sky.vo.DishVo;
import common.sky.result.PageResult;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:03
 */
public interface DishService {
    void addDish(DishDto dishDto);

    PageResult page(DishPageQueryDto dto);

    void delete(List<Long> ids);

    DishVo getById(Long id);

    void update(DishDto dto);
}
