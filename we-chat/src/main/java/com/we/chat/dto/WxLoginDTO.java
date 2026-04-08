package com.we.chat.dto;

import lombok.Data;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  9:45
 */
@Data
public class WxLoginDTO {
    private String code;
    private String nickname;
    private String avatar;
}
