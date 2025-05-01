package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.entity.Employee;
import common.sky.enumeration.OperationType;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  14:53
 */
public interface EmployeeMapper {
    Employee getByUsername(String username);
    @AutoFill(OperationType.INSERT)
    void insert(Employee employee);

    Page<Employee> list(String name);
    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);

    Employee getById(Long id);
}
