package com.we.chat.entity;
import lombok.Data;
import java.time.LocalDateTime;
/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  9:42
 */
@Data
public class WxUser {
    private Long id;
    private String openid;
    private String nickname;
    private String avatar;
    private String token;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}