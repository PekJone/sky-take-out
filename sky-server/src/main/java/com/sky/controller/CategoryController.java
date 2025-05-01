package com.sky.controller;

import com.sky.dto.CategoryDto;
import com.sky.service.CategoryService;
import common.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Result<String> save(@RequestBody CategoryDto categoryDto){
        log.info("新增分类{}",categoryDto);
        categoryService.save(categoryDto);
        return Result.success();
    }
}
