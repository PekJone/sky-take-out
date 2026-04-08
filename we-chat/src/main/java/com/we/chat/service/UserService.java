package com.we.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.we.chat.entity.User;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  15:56
 */
public interface UserService extends IService<User> {
        User getByOpenId(String openId);
}
