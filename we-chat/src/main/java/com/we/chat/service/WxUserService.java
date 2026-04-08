package com.we.chat.service;

import com.we.chat.common.Result;
import com.we.chat.dto.WxLoginDTO;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  9:50
 */
public interface WxUserService {
    Result<?> wxLogin(WxLoginDTO dto);
}
