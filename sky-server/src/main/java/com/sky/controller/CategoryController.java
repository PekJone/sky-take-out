package com.sky.controller;

import com.sky.anno.AutoFill;
import com.sky.dto.CategoryDto;
import com.sky.dto.CategoryPageQueryDto;
import com.sky.service.CategoryService;
import common.sky.result.PageResult;
import common.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:14
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
       private CategoryService categoryService;
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/save")
    public Result<String> save(@RequestBody CategoryDto categoryDto){
        log.info("新增分类{}",categoryDto);
        categoryService.save(categoryDto);
        return Result.success();
    }

    @PostMapping("/page")
    public Result<PageResult> page(@RequestBody CategoryPageQueryDto categoryPageQueryDto){
        log.info("分页分类查询：{}",categoryPageQueryDto);
        PageResult pageResult=categoryService.page(categoryPageQueryDto);
        return Result.success(pageResult);

    }
}
