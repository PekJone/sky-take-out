package com.we.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.we.chat.entity.Product;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  15:54
 */
public interface ProductService extends IService<Product> {
    List<Product> getList();

    Product getDetail(Long id);
}
