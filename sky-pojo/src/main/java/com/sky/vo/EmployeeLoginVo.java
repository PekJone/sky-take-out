package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginVo {

    private long id ;

    private String name;

    private String username;

    private String token;
}
