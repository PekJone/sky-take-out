package com.sky.controller;

import com.sky.dto.EmployeeDto;
import com.sky.dto.EmployeeLoginDto;
import com.sky.dto.EmployeePageQueryDto;
import com.sky.entity.Employee;
import com.sky.service.EmployeeService;
import com.sky.vo.EmployeeLoginVo;
import common.sky.constant.JwtClaimsConstant;
import common.sky.properties.JwtProperties;
import common.sky.result.PageResult;
import common.sky.result.Result;
import common.sky.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:02
 */
@Tag(name = "员工管理", description = "员工登录和基础操作")
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    private EmployeeService employeeService;
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private JwtProperties jwtProperties;

    public void setJwtProperties(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Operation(summary = "测试接口")
    @GetMapping("/test")
    public void test(){
        System.out.println("this is test");
    }


    @Operation(summary = "员工登录", description = "返回JWT令牌", responses = {
            @ApiResponse(responseCode = "200", description = "登录成功"),
            @ApiResponse(responseCode = "401", description = "用户名或密码错误")
    })
    @PostMapping("/login")
    public Result<EmployeeLoginVo> login(@RequestBody EmployeeLoginDto employeeDto) throws AccountNotFoundException {
        log.info("员工登录{}",employeeDto);
        Employee employee = employeeService.login(employeeDto);

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,employee.getId());
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(),jwtProperties.getAdminTtl(),claims);

        EmployeeLoginVo employeeLoginVo = EmployeeLoginVo.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();
        return Result.success(employeeLoginVo);
    }
    @Operation(summary = "员工退出")
    @PostMapping("/logout")
    public Result<String> logout(){
        return Result.success();
    }
    @Operation(summary = "新增员工")
    @PostMapping("/addEmp")
    public Result addEmp(@RequestBody EmployeeLoginDto employeeLoginDto){
        log.info("新增员工{}",employeeLoginDto);
        employeeService.addEmp(employeeLoginDto);
        log.info("新增员工OK");
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> pages(EmployeePageQueryDto dto){
        log.info("分页查询");
        PageResult pageResult = employeeService.page(dto);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    public Result enableDisable(@PathVariable Integer status,Long id){
       log.info("员工状态修改status={},id={}",status,id);
       employeeService.enableOrDisable(status,id);
       return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("员工id={}",id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);

    }

    @PostMapping("/update")
    public Result update(@RequestBody EmployeeDto employeeDto){
        log.info("修改员工={}",employeeDto);
        employeeService.updateEmp(employeeDto);
        return Result.success();
    }

}
