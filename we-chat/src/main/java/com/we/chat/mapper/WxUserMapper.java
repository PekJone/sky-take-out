package com.we.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.we.chat.entity.WxUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  9:47
 */
public interface WxUserMapper extends BaseMapper<WxUser> {
    WxUser selectByOpenid(@Param("openId") String openId);

    int updateUser(WxUser wxUser);
}
