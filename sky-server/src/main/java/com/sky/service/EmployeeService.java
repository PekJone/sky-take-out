package com.sky.service;

import com.sky.dto.EmployeeLoginDto;
import com.sky.entity.Employee;

import javax.security.auth.login.AccountNotFoundException;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:03
 */
public interface EmployeeService {

    Employee login(EmployeeLoginDto employeeDto) throws AccountNotFoundException;

    void addEmp(EmployeeLoginDto employeeLoginDto);
}
