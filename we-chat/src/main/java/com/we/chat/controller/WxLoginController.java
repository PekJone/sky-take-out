package com.we.chat.controller;

import com.we.chat.common.Result;
import com.we.chat.dto.WxLoginDTO;
import com.we.chat.service.WxUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  10:07
 */
@RestController
@RequestMapping("/api/wx")
public class WxLoginController {
    @Resource
    private WxUserService wxUserService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody WxLoginDTO dto){
        return wxUserService.wxLogin(dto);
    }
}
