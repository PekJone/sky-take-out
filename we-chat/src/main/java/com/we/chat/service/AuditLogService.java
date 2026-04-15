package com.we.chat.service;

import com.we.chat.entity.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  17:34
 */
@Service
@Slf4j
public class AuditLogService {
    public void logPass(RequestContext ctx) {
        log.info("[AUDIT] allow userId={} uri={} traceId={}",
                ctx.getUserId(), ctx.getUri(), ctx.getTraceId());
    }

    public void logDeny(RequestContext ctx, String reason) {
        log.warn("[AUDIT] deny userId={} uri={} reason={} traceId={}",
                ctx.getUserId(), ctx.getUri(), reason, ctx.getTraceId());
    }
}
