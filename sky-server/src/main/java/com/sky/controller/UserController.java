package com.sky.controller;

import com.sky.dto.UserLoginDto;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.service.UserService;
import com.sky.service.imip.UserServiceImpl;
import com.sky.vo.UserLoginVo;
import common.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-07  9:56
 */
@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<UserLoginVo> login(@RequestBody UserLoginDto userLoginDto){
        log.info("用户登录");
        User user = userService.login(userLoginDto);

        UserLoginVo userLoginVo = UserLoginVo.builder()
                .id(user.getId())
                .openId(user.getOpenid())
                .token("sfsdafdsafsd")
                .build();

        return Result.success(userLoginVo);
    }
}
