package com.sky.service;

import com.sky.dto.EmployeeDto;
import com.sky.dto.EmployeeLoginDto;
import com.sky.dto.EmployeePageQueryDto;
import com.sky.entity.Employee;
import common.sky.result.PageResult;

import javax.security.auth.login.AccountNotFoundException;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:03
 */
public interface EmployeeService {

    Employee login(EmployeeLoginDto employeeDto) throws AccountNotFoundException;

    void addEmp(EmployeeLoginDto employeeLoginDto);

    PageResult page(EmployeePageQueryDto dto);

    void enableOrDisable(Integer status, Long id);

    Employee getById(Long id);

    void updateEmp(EmployeeDto employeeDto);
}
