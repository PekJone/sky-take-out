package com.sky.service;

import com.sky.dto.CategoryDto;
import com.sky.dto.CategoryPageQueryDto;
import common.sky.result.PageResult;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:15
 */
public interface CategoryService {
    void save(CategoryDto categoryDto);

    PageResult page(CategoryPageQueryDto categoryPageQueryDto);
}
