package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-07  9:52
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo implements Serializable {
    private Long id;
    private String openId;

    private String token;
}
