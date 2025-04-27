package com.sky.handler;

import common.sky.exception.BaseException;
import common.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-27  15:25
 */
@Slf4j
public class GlobalExceptionHandler {
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}",ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
