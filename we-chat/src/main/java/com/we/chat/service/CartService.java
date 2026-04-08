package com.we.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.we.chat.entity.Cart;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  15:57
 */
public interface CartService extends IService<Cart> {
    boolean addCart(Long userId, Long productId);
    List<Cart> getCartList(Long userId);
}
