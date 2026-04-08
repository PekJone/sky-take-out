package com.we.chat.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  15:43
 */
@Slf4j
@Component
@Order(1)//设置过滤器执行顺序，值越小优先执行
public class CorsAndLogFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //统一设置请求和响应的编码格式，解决中文乱码问题
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        //跨域设置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "token,Content-Type");
        response.setHeader("Access-Control-Max-age", "3600");
        //放行Option预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        // 3. 打印请求日志
        log.info("请求URI：{}，请求IP：{}", request.getRequestURI(), request.getRemoteAddr());

        // 4. 放行
        filterChain.doFilter(request, response);
    }


}
