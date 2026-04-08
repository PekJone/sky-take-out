package com.we.chat.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.we.chat.entity.User;
import com.we.chat.mapper.UserMapper;
import com.we.chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  16:01
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getByOpenId(String openid) {
        return lambdaQuery().eq(User::getOpenid, openid).one();
    }


}
