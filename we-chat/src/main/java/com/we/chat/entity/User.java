package com.we.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  15:45
 */
@Data
@TableName("user")
public class User {
    private Long id;
    private String openid;
    private String nickname;
    private String avatar;
    private Date createTime;
}
