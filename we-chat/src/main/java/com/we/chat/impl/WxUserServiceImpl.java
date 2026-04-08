package com.we.chat.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.chat.common.Result;
import com.we.chat.dto.WxLoginDTO;
import com.we.chat.entity.WxUser;
import com.we.chat.mapper.WxUserMapper;
import com.we.chat.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  9:52
 */
@Service
@Slf4j
public class WxUserServiceImpl implements WxUserService {
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;
    @Resource
    private WxUserMapper wxUserMapper;

    private final RestTemplate restTemplate = new RestTemplate();
        // 用来把 String 转 JSON
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public Result<?> wxLogin(WxLoginDTO dto) {
            try {
                // 1. 微信接口地址
                String url = "https://api.weixin.qq.com/sns/jscode2session"
                        + "?appid=" + appid
                        + "&secret=" + appsecret
                        + "&js_code=" + dto.getCode()
                        + "&grant_type=authorization_code";

                // 2. 用 String 接收，解决 text/plain 问题（关键修复）
                org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
                String responseStr = restTemplate.getForObject(url, String.class);

                // 3. 把 String 转 Map
                Map<String, Object> res = objectMapper.readValue(responseStr, Map.class);

                // 4. 判断是否成功
                if (res == null || !res.containsKey("openid")) {
                    return Result.error("登录凭证无效");
                }

                String openid = res.get("openid").toString();
                String token = RandomStringUtils.randomAlphanumeric(32);

                // 5. 查询用户
                WxUser user = wxUserMapper.selectByOpenid(openid);
                if (user == null) {
                    user = new WxUser();
                    user.setOpenid(openid);
                    user.setNickname(dto.getNickname());
                    user.setAvatar(dto.getAvatar());
                    user.setToken(token);
                    log.info("新用户登录，openid: {}, nickname: {}", openid, dto.getNickname());
                    wxUserMapper.insert(user);
                } else {
                    user.setNickname(dto.getNickname());
                    user.setAvatar(dto.getAvatar());
                    user.setToken(token);
                    log.info("老用户登录，openid: {}, nickname: {}", openid, dto.getNickname());
                    wxUserMapper.updateUser(user);
                }

                // 6. 返回前端
                Map<String, String> data = new HashMap<>();
                data.put("userId", openid);
                data.put("nickname", dto.getNickname());
                data.put("avatar", dto.getAvatar());
                data.put("token", token);

                return Result.success(data);

            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("登录异常：" + e.getMessage());
            }
        }
    }

