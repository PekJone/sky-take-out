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
import com.sky.mapper.SetMealDishMapper;
import com.sky.service.DishService;
import com.sky.vo.DishVo;
import common.sky.constant.MessageConstant;
import common.sky.constant.StatusConstant;
import common.sky.context.BaseContext;
import common.sky.exception.DeleteNotAllowedException;
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

    private SetMealDishMapper setMealDishMapper;
    @Autowired
    public void setSetMealDishMapper(SetMealDishMapper setMealDishMapper) {
        this.setMealDishMapper = setMealDishMapper;
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

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        ids.forEach(id->{
            Dish dish = dishMapper.selectById(id);
            if (dish.getStatus()== StatusConstant.DISABLE){
                throw new DeleteNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });
        //如果菜品关联了套餐 不能删除
        Integer count = setMealDishMapper.countByDishId(ids);
        if(count>0){
            throw new DeleteNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //删除菜品基本信息
        dishMapper.deleteBatch(ids);

        dishFlavorMapper.deleteBatch(ids);
    }

    @Override
    public DishVo getById(Long id) {
        DishVo dishVo = new DishVo();
        Dish dish = dishMapper.selectById(id);
        BeanUtils.copyProperties(dish,dishVo);
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectByDishId(id);
        dishVo.setFlavors(dishFlavors);
        return dishVo;
    }
    @Transactional //涉及到两张表的加事物
    @Override
    public void update(DishDto dto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto,dish);
        //1.修改菜品的基本信息
        dishMapper.update(dish);
        //2.修改口味列表信息  先删除旧数据 再新增所有
        dishFlavorMapper.deleteByDishId(dto.getId());
        List<DishFlavor> flavors = dto.getFlavors();
        if(flavors!=null &&flavors.isEmpty()){
            flavors.forEach(flavor->{
                flavor.setDishId(dish.getId());
            });
        }
        dishFlavorMapper.addBatch(flavors);


    }
}
