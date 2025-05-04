package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.CategoryPageQueryDto;
import com.sky.entity.Category;
import common.sky.enumeration.OperationType;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  9:29
 */
public interface CategoryMapper {
    @AutoFill(OperationType.INSERT)
    void save(Category category);


    Page<Category> pageQuery(CategoryPageQueryDto categoryPageQueryDto);
}
