package com.we.chat.common;

import lombok.Data;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-07  15:36
 */

public enum ResultCode {
    SUCCESS(200, "成功"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未登录/Token过期"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    SYSTEM_ERROR(500, "服务器繁忙，请稍后再试"),
    BUSINESS_ERROR(600, "业务异常"),
    FLOW_LIMIT(601, "系统繁忙，限流中"),
    DEGRADE(602, "服务降级，请稍后再试"),
    REMOTE_ERROR(603, "调用第三方服务失败"),
    DB_ERROR(604, "数据库异常"),
    ;

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
