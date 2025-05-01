package com.sky.controller;

import com.sky.service.DishService;
import common.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
