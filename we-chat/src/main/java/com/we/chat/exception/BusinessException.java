package com.we.chat.exception;

import com.we.chat.common.ResultCode;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-07  15:37
 */
public class BusinessException extends RuntimeException{
    private int    code;
    private String msg;

    public BusinessException(String msg) {
        super(msg);
        this.code = ResultCode.BUSINESS_ERROR.getCode();
        this.msg = msg;
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
