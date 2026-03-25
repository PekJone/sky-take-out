package com.oa.common.result;

import com.oa.common.constant.HttpStatus;

import java.io.Serializable;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-01-07  10:20
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(HttpStatus.SUCCESS, "操作成功", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(HttpStatus.SUCCESS, message, data);
    }

    public static <T> Result<T> fail() {
        return fail("操作失败");
    }

    public static <T> Result<T> fail(String msg) {
        return fail(HttpStatus.ERROR, msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> fail(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public boolean isSuccess() {
        return this.code == HttpStatus.SUCCESS;
    }
}
