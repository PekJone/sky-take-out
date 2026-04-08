package com.we.chat.controller;

import com.we.chat.entity.Cart;
import com.we.chat.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  16:19
 */
@RestController
@Slf4j
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Map<String, Object> add(Long userId, Long productId) {
        boolean b = cartService.addCart(userId, productId);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("success", b);
        return map;
    }

    @GetMapping("/list")
    public List<Cart> list(Long userId) {
        return cartService.getCartList(userId);
    }
}
