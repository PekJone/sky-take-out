package com.we.chat.filter;

import com.we.chat.annotate.RepeatSubmit;
import com.we.chat.util.RedisLockUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  16:51
 */
@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class RepeatSubmitFilter implements Filter {
    private final RedisLockUtil redisLockUtil;
    private final RequestMappingHandlerMapping handlerMapping;

    private static final String LOCK_KEY_PREFIX = "repeat_submit:";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            // 1. 获取当前请求对应的Controller方法
            HandlerExecutionChain chainHandler = handlerMapping.getHandler(req);
            if (chainHandler == null) {
                chain.doFilter(request, response);
                return;
            }
            HandlerMethod handlerMethod = (HandlerMethod) chainHandler.getHandler();

            // 2. 判断方法是否有 @RepeatSubmit 注解
            RepeatSubmit repeatSubmit = handlerMethod.getMethodAnnotation(RepeatSubmit.class);
            if (repeatSubmit == null) {
                chain.doFilter(request, response);
                return;
            }

            // 3. 构造唯一key：用户标识 + URI
            // 真实项目从token解析userId
            String userId = req.getHeader("userId");
            if (userId == null) userId = "unknown";

            String uri = req.getRequestURI();
            String lockKey = LOCK_KEY_PREFIX + userId + ":" + uri;
            int expire = repeatSubmit.expire();

            // 4. 加锁
            boolean lockSuccess = redisLockUtil.lock(lockKey, expire);
            if (!lockSuccess) {
                log.warn("重复提交拦截: userId={}, uri={}", userId, uri);
                returnError(resp, "请勿重复提交");
                return;
            }

            // 5. 放行
            chain.doFilter(request, response);

        } catch (Exception e) {
            chain.doFilter(request, response);
        }
    }

    /**
     * 返回错误信息
     */
    private void returnError(HttpServletResponse resp, String msg) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setStatus(200);
        PrintWriter writer = resp.getWriter();
        writer.write("{\"code\":601,\"msg\":\"" + msg + "\"}");
        writer.flush();
        writer.close();
    }
}
