package com.oa.common.exception;

import com.oa.common.constant.HttpStatus;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-01-07  10:05
 */
public class BusinessException extends BaseException{
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message, HttpStatus.ERROR);
    }

    public BusinessException(String message, Integer code) {
        super(message, code);
    }
}
