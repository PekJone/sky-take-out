package com.sky.controller;

import com.sky.dto.DishDto;
import com.sky.dto.DishPageQueryDto;
import com.sky.service.DishService;
import com.sky.vo.DishVo;
import common.sky.result.PageResult;
import common.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:02
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    private DishService dishService;
    @Autowired
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping("/addDish")
    public Result addDish(@RequestBody DishDto dishDto){
        log.info("新增菜品{}",dishDto);
        dishService.addDish(dishDto);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDto dto){
        log.info("分页查询菜品{}",dto);
        PageResult pageResult= dishService.page(dto);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
        log.info("删除菜品{}",ids);
        dishService.delete(ids);
        return Result.success();
    }

    @GetMapping("/id")
    public Result<DishVo> getById(@PathVariable Long id){
        log.info("回显菜品{}",id);
        DishVo dishVo = dishService.getById(id);
        return Result.success(dishVo);
    }

    public Result update(@RequestBody DishDto dto){
         log.info("修改菜品{}",dto);
         dishService.update(dto);
         return Result.success();
    }
}
