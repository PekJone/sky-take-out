package com.we.chat.controller;

import com.we.chat.entity.Product;
import com.we.chat.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-06  16:16
 */
@RestController
@Slf4j
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> list() {
        return productService.getList();
    }

    @GetMapping("/detail")
    public Product detail(Long id) {
        return productService.getDetail(id);
    }
}
