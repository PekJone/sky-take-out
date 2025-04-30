package com.sky.handler;

import common.sky.exception.BaseException;
import common.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-27  15:25
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}",ex.getMessage());
        return Result.error(ex.getMessage());
    }
    @ExceptionHandler
    public Result doSQLException(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息{}",ex.getMessage());
        String message = ex.getMessage();
        if(message.contains("Duplicate")){
           String[] split =  message.split(" ");
           String mes = split[2];
            return Result.error(mes+"已存在");
        }
        return Result.error("XXX已存在");
    }
}
