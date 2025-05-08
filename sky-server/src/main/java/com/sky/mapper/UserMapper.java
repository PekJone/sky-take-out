package com.sky.mapper;

import com.sky.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Options;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-07  10:10
 */
public interface UserMapper {
    User selectByOpenId(String openid);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(User user);
}
