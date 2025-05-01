package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-30  15:37
 */
@Data
public class EmployeePageQueryDto implements Serializable {
    private String name;

    private int page ;

    private int pageSize ;
}
