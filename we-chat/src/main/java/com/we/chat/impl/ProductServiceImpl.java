package com.we.chat.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.we.chat.entity.Product;
import com.we.chat.mapper.ProductMapper;
import com.we.chat.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  15:58
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public List<Product> getList() {
        return null;
    }

    @Override
    public Product getDetail(Long id) {
        return null;
    }
}
