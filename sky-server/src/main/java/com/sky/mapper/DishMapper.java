package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.DishPageQueryDto;
import com.sky.entity.Dish;
import com.sky.vo.DishVo;
import common.sky.enumeration.OperationType;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  17:04
 */
public interface DishMapper {
    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void addDish(Dish dish);

    Page<DishVo> page(DishPageQueryDto dto);

    Dish selectById(Long id);

    void deleteBatch(List<Long> ids);
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
}
