package com.we.chat.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  15:55
 */
@Component
@Slf4j
@Order(2) //设置过滤器执行顺序，值越小优先执行
public class AuthFilter implements Filter {
    // 放行白名单
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/user/login",
            "/user/register",
            "/doc.html",        // knife4j
            "/webjars/**",
            "/v2/api-docs"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // 白名单直接放行
        if (isWhiteList(uri)) {
            chain.doFilter(request, response);
            return;
        }

        // 获取 token
        String token = req.getHeader("token");
        if (token == null || token.trim().isEmpty()) {
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"code\":401,\"msg\":\"未登录，请先登录\"}");
            return;
        }

        // 校验 token（这里可调用 JWT 工具类）
        boolean valid = checkToken(token);
        if (!valid) {
            resp.getWriter().write("{\"code\":401,\"msg\":\"token 无效或过期\"}");
            return;
        }

        // 放行
        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String uri) {
        return WHITE_LIST.stream().anyMatch(uri::startsWith);
    }

    private boolean checkToken(String token) {
        // 真实项目：JWTUtil.verifyToken(token)
        return "valid_token".equals(token);
    }

}
