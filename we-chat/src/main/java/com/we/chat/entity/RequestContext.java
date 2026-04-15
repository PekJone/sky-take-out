package com.we.chat.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  17:23
 */
@Data
public class RequestContext {
    private String  traceId;
    private String  spanId;
    private String  ip;
    private String  uri;
    private String  token;
    private Long    userId;
    private String  username;
    private List<String> roles;
    private List<String> permissions;
    private String  tenantId;
    private String  grayVersion;
    private boolean isInternal;
    private boolean isAdmin;
}