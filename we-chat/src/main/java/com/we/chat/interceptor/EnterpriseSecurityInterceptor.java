package com.we.chat.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.we.chat.common.Result;
import com.we.chat.entity.ContextHolder;
import com.we.chat.entity.RequestContext;
import com.we.chat.entity.WhiteListProperties;
import com.we.chat.service.AuditLogService;
import com.we.chat.service.PermissionService;
import com.we.chat.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  17:29
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EnterpriseSecurityInterceptor implements HandlerInterceptor {
    private final WhiteListProperties whiteList;
    private final JWTUtil jwtUtil;
    private final PermissionService permissionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuditLogService auditLogService;

    private static final String[] INTERNAL_HEADERS = {"X-Internal-Service", "X-App-Id"};
    private static final String GRAY_HEADER = "X-Gray-Version";
    private static final String TENANT_HEADER = "X-Tenant-Id";
    private static final String NONCE_HEADER = "X-Nonce";
    private static final String TIMESTAMP_HEADER = "X-Timestamp";
    private static final long REPLAY_WINDOW = 60 * 1000;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String traceId = IdUtil.fastSimpleUUID();
        String spanId = IdUtil.fastSimpleUUID();
        MDC.put("traceId", traceId);

        RequestContext ctx = new RequestContext();
        ctx.setTraceId(traceId);
        ctx.setSpanId(spanId);
        ctx.setUri(request.getRequestURI());
        ctx.setIp(getRealIp(request));
        ctx.setGrayVersion(request.getHeader(GRAY_HEADER));
        ctx.setTenantId(request.getHeader(TENANT_HEADER));

        ContextHolder.set(ctx);

        // 日志结构化
        log.info("[REQUEST] uri={} ip={} traceId={}",
                ctx.getUri(), ctx.getIp(), ctx.getTraceId());

        // 白名单匹配
        if (isWhiteList(ctx.getUri())) {
            return true;
        }

        // 内部服务免鉴权
        if (isInternalRequest(request)) {
            ctx.setInternal(true);
            return true;
        }

        // 防重放校验
        if (!validateReplay(request)) {
            writeJson(response, 403, "请求已过期或重复提交");
            return false;
        }

        // Token 校验
        String token = getToken(request);
        if (StrUtil.isBlank(token)) {
            writeJson(response, 401, "未登录");
            return false;
        }

        if (!jwtUtil.validateToken(token)) {
            writeJson(response, 401, "token过期或无效");
            return false;
        }

        // 解析用户信息
        Long userId = jwtUtil.getUserId(token);
        String username = jwtUtil.getUsername(token);
        List<String> roles = jwtUtil.getRoles(token);
        List<String> permissions = permissionService.getPermissions(userId);

        ctx.setToken(token);
        ctx.setUserId(userId);
        ctx.setUsername(username);
        ctx.setRoles(roles);
        ctx.setPermissions(permissions);
        ctx.setAdmin(roles.contains("admin"));

        // 权限校验
        if (!ctx.isAdmin() && !permissionService.hasPermission(ctx.getUri(), permissions)) {
            writeJson(response, 403, "无权限访问");
            auditLogService.logDeny(ctx, "无接口权限");
            return false;
        }

        // 限流校验
        if (isRateLimited(userId)) {
            writeJson(response, 429, "请求过于频繁");
            return false;
        }

        // 审计日志
        auditLogService.logPass(ctx);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        ContextHolder.clear();
        MDC.clear();
    }

    // ====================== 工具方法 ==========================

    private boolean isWhiteList(String uri) {
        return whiteList.getExact().contains(uri)
                || whiteList.getPrefix().stream().anyMatch(uri::startsWith)
                || whiteList.getContain().stream().anyMatch(uri::contains);
    }

    private String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StrUtil.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private boolean isInternalRequest(HttpServletRequest request) {
        for (String h : INTERNAL_HEADERS) {
            if (StrUtil.isNotBlank(request.getHeader(h))) {
                return true;
            }
        }
        return false;
    }

    private boolean validateReplay(HttpServletRequest request) {
        String nonce = request.getHeader(NONCE_HEADER);
        String tsStr = request.getHeader(TIMESTAMP_HEADER);
        if (StrUtil.hasBlank(nonce, tsStr)) return true; // 可选开启

        long ts = Long.parseLong(tsStr);
        if (System.currentTimeMillis() - ts > REPLAY_WINDOW) return false;

        String key = "replay:" + nonce;
        return Boolean.TRUE.equals(redisTemplate.opsForValue()
                .setIfAbsent(key, "1", REPLAY_WINDOW, TimeUnit.MILLISECONDS));
    }

    private String getToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return request.getHeader("token");
    }

    private boolean isRateLimited(Long userId) {
        String key = "rate:user:" + userId;
        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            redisTemplate.expire(key, 1, TimeUnit.SECONDS);
        }
        return count != null && count > 100;
    }

    private void writeJson(HttpServletResponse response, int code, String msg) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(Result.fail(code, msg)));
    }
}
