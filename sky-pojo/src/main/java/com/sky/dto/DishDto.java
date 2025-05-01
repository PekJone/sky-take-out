package com.sky.dto;

import com.sky.entity.DishFlavor;
import lombok.Data;
import org.apache.ibatis.annotations.Lang;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  16:57
 */
@Data
public class DishDto implements Serializable {
    private Long id ;
    private String name ;

    private Long categoryId;

    private BigDecimal price;

    private String image;

    private String description;

    private Integer status;

    private List<DishFlavor> flavors = new ArrayList<>();
}
