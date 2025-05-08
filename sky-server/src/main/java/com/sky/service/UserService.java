package com.sky.service;

import com.sky.dto.UserLoginDto;
import com.sky.entity.User;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-07  10:01
 */
public interface UserService {

    User login(UserLoginDto userLoginDto);
}
