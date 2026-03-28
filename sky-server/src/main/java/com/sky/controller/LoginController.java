package com.sky.controller;

import com.sky.utils.WtUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-28  9:34
 */
@RestController
@RequestMapping("/admin/login")
public class LoginController {
    @Resource
    private WtUtils wtUtils;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password) {
        // 1. 校验用户名密码（略）
        if (!"admin".equals(username) || !"123456".equals(password)) {
            return Map.of("code", 401, "msg", "用户名或密码错误");
        }

        // 2. 存入自定义信息（不要放敏感信息！）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 1001L);
        claims.put("username", username);

        // 3. 生成 token
        String token = wtUtils.generateToken(claims);

        return Map.of(
                "code", 200,
                "msg", "登录成功",
                "token", token
        );
    }
}
