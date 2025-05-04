package com.sky.service.imip;

import com.alibaba.druid.sql.dialect.blink.parser.BlinkStatementParser;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDto;
import com.sky.dto.DishPageQueryDto;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import com.sky.vo.DishVo;
import common.sky.context.BaseContext;
import common.sky.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:03
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {
    private DishMapper dishMapper;
    @Autowired
    public void setDishMapper(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }

    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    public void setDishFlavorMapper(DishFlavorMapper dishFlavorMapper) {
        this.dishFlavorMapper = dishFlavorMapper;
    }

    @Transactional
    @Override
    public void addDish(DishDto dishDto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto,dish);
        System.out.println("我能够看到这个ID吗 " +BaseContext.getCurrentId());
        dishMapper.addDish(dish);

        log.info("dishId={}",dish.getId());
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        dishFlavors.forEach(dishFlavor -> {
            dishFlavor.setDishId(dish.getId());
        });

        dishFlavorMapper.addBatch(dishFlavors);

    }

    @Override
    public PageResult page(DishPageQueryDto dto) {
        PageHelper.startPage(dto.getPage(),dto.getPageSize());
        Page<DishVo> page = dishMapper.page(dto);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());
        return pageResult;
    }
}
