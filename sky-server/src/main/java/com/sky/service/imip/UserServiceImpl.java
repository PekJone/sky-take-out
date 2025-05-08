package com.sky.service.imip;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.sky.dto.UserLoginDto;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpUtil;
import common.sky.constant.MessageConstant;
import common.sky.exception.LoginFaildException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-07  10:02
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
     private WeChatProperties weChatProperties;
     private UserMapper userMapper;
   @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setWeChatProperties(WeChatProperties weChatProperties) {
        this.weChatProperties = weChatProperties;
    }

    @Override
    public User login(UserLoginDto userLoginDto) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid",weChatProperties.getAppid());
        paramMap.put("secret",weChatProperties.getSecret());
        paramMap.put("js_code", userLoginDto.getCode());
        paramMap.put("grant_type","0authorization_code");
        String res = HttpUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", paramMap);
        log.info("res={}",res);
        JSONObject obj = JSON.parseObject(res);
        String openid =(String)obj.get("openid");
        if(openid ==null){
             throw new LoginFaildException(MessageConstant.USER_NOT_LOGIN);
        }
        User user = userMapper.selectByOpenId(openid);
        if(user == null){
            user = new User();
            user.setOpenid(openid);
            user.setCreateTime(LocalDateTime.now());
            user.setName(openid.substring(0,5));
            userMapper.insert(user);
        }

        return user;
    }
}
