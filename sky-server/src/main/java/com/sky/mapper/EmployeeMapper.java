package com.sky.mapper;

import com.sky.entity.Employee;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  14:53
 */
public interface EmployeeMapper {
    Employee getByUsername(String username);

    void insert(Employee employee);
}
