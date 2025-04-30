package com.sky.dto;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:22
 */
@Data
public class EmployeeLoginDto {
    private String username;
    private String password;
    private String name;
    private String idNumber ;

    private String phone;

    private String sex;

    private Long id;
}
