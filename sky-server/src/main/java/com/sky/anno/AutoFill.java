package com.sky.anno;

import common.sky.enumeration.OperationType;
import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  9:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoFill {
    //用来标识是新增还是修改
    OperationType value();
}
