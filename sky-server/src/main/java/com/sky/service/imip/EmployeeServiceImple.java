package com.sky.service.imip;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.protocol.x.MessageConstants;
import com.sky.dto.EmployeeDto;
import com.sky.dto.EmployeeLoginDto;
import com.sky.dto.EmployeePageQueryDto;
import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import common.sky.constant.MessageConstant;
import common.sky.constant.StatusConstant;
import common.sky.context.BaseContext;
import common.sky.exception.AccountLockerException;
import common.sky.exception.AccountNotFoundException;
import common.sky.exception.PasswordErrorException;
import common.sky.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:06
 */
@Service
public class EmployeeServiceImple implements EmployeeService {
    private EmployeeMapper employeeMapper;
    @Autowired
    public void setEmployeeMapper(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Employee login(EmployeeLoginDto employeeDto)  {

        String username = employeeDto.getUsername();
        String password = employeeDto.getPassword();
        Employee employee = employeeMapper.getByUsername(username);
        if(employee == null){
            throw  new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUNT);
        }
        if(!password.equals(employee.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if(employee.getStatus()== StatusConstant.DISABLE){
            throw  new AccountLockerException(MessageConstant.ACCOUNT_LOCK);
        }
        return employee;
    }

    /**
     * 新增员工
     * @param employeeLoginDto
     */
    @Override
    public void addEmp(EmployeeLoginDto employeeLoginDto) {
        //补齐缺失的属性
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeLoginDto,employee);
        employee.setStatus(StatusConstant.ABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(10L);
        employee.setUpdateUser(10L);
        employee.setUpdateUser(BaseContext.getCurrentId());
        //调用mapper的新增方法存入数据库
        employeeMapper.insert(employee);


    }

    @Override
    public PageResult page(EmployeePageQueryDto dto) {
        PageHelper.startPage(dto.getPage(),dto.getPageSize());
        Page<Employee> page = employeeMapper.list(dto.getName());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void enableOrDisable(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee employee =  employeeMapper.getById(id);
        return employee;
    }

    @Override
    public void updateEmp(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto,employee);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }
}
