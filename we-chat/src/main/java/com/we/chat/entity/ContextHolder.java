package com.we.chat.entity;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  17:25
 */
public class ContextHolder {private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

    public static void set(RequestContext ctx) {
        CONTEXT.set(ctx);
    }

    public static RequestContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

}
