package com.sky.service.imip;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.CategoryDto;
import com.sky.dto.CategoryPageQueryDto;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import common.sky.constant.StatusConstant;
import common.sky.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.constant.Constable;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:16
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryMapper categoryMapper;
    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto,category);
        category.setStatus(StatusConstant.ABLE);
        categoryMapper.save(category);
}

    @Override
    public PageResult page(CategoryPageQueryDto categoryPageQueryDto) {
        PageHelper.startPage(categoryPageQueryDto.getPage(), categoryPageQueryDto.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDto);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());
        return pageResult;
    }
}
