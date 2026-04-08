package com.we.chat.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.we.chat.entity.Cart;
import com.we.chat.mapper.CartMapper;
import com.we.chat.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  16:04
 */
@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService{

    @Override
    public boolean addCart(Long userId, Long productId) {
        //查询是否已经存在
        Cart cart = lambdaQuery().eq(Cart::getUserId, userId).eq(Cart::getProductId, productId).one();
        if (cart != null) {
            cart.setNum(cart.getNum() + 1);
            return updateById(cart);
        }else{
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setNum(1);
            return save(cart);
        }
    }

    @Override
    public List<Cart> getCartList(Long userId) {
        return lambdaQuery().eq(Cart::getUserId, userId).list();
    }
}
