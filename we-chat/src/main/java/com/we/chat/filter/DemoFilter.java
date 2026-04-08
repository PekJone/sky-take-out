package com.we.chat.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  15:36
 */
@Slf4j
@WebServlet(urlPatterns = "/*", name = "demoFilter")
public class DemoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       log.info("DemoFilter 初始化完整");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        log.info("请求进入过滤器执行，URI: {}", uri);
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("请求离开过滤器执行，URI: {}", uri);
    }

    @Override
    public void destroy() {
        log.info("DemoFilter 销毁完成");
    }
}
