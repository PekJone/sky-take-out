package com.we.chat.config;

import com.we.chat.common.Result;
import com.we.chat.common.ResultCode;
import com.we.chat.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-07  15:41
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<?>  businessExceptionHandler(BusinessException e, HttpServletRequest request){
        String requestId = request.getAttribute("requestId").toString();
        log.error("[业务异常] requestId:{} msg:{}", requestId, e.getMessage());
        Result<?> r = Result.fail(e.getCode(), e.getMessage());
        r.setRequestId(requestId);
        return r;
    }
    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        FieldError error = e.getBindingResult().getFieldError();
        String msg = error.getField() + ": " + error.getDefaultMessage();
        log.error("[参数校验异常] {}", msg);
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);
    }


    /**
     * 3. GET 表单参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        FieldError error = e.getFieldError();
        String msg = error.getField() + ": " + error.getDefaultMessage();
        log.error("[参数绑定异常] {}", msg);
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    /**
     * 4. 单个参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String msg = violations.iterator().next().getMessage();
        log.error("[参数校验异常] {}", msg);
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    /**
     * 5. 缺少参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingParam(MissingServletRequestParameterException e) {
        String msg = "缺少参数：" + e.getParameterName();
        log.error(msg);
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    /**
     * 6. 请求体格式错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleMessageNotReadable(HttpMessageNotReadableException e) {
        log.error("请求体解析失败：", e);
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), "请求参数格式错误");
    }

    /**
     * 7. 404 不存在
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> handleNotFound(NoHandlerFoundException e) {
        log.error("404 不存在：{}", e.getRequestURL());
        return Result.fail(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMsg());
    }

    /**
     * 8. 请求方法错误（POST/GET）
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String msg = "不支持" + e.getMethod() + "请求";
        log.error(msg);
        return Result.fail(ResultCode.METHOD_NOT_ALLOWED.getCode(), msg);
    }

    /**
     * 9. 数据库异常
     */
    @ExceptionHandler(DataAccessException.class)
    public Result<?> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        log.error("[数据库异常] uri:{} ", request.getRequestURI(), e);
        return Result.fail(ResultCode.DB_ERROR.getCode(), "数据服务异常");
    }

    /**
     * 10. 限流/降级（Sentinel）
     */
//    @ExceptionHandler({com.alibaba.csp.sentinel.slots.block.flow.FlowException.class,
//            com.alibaba.csp.sentinel.slots.block.degrade.DegradeException.class})
//    public Result<?> handleSentinelException(Exception e) {
//        log.error("[限流/降级] ", e);
//        if (e instanceof com.alibaba.csp.sentinel.slots.block.flow.FlowException) {
//            return Result.fail(ResultCode.FLOW_LIMIT.getCode(), ResultCode.FLOW_LIMIT.getMsg());
//        } else {
//            return Result.fail(ResultCode.DEGRADE.getCode(), ResultCode.DEGRADE.getMsg());
//        }
//    }

    /**
     * 11. 权限异常（Shiro/SpringSecurity）
     */
//    @ExceptionHandler({org.apache.shiro.authz.UnauthorizedException.class,
//            org.springframework.security.access.AccessDeniedException.class})
//    public Result<?> handleAccessDeniedException(Exception e) {
//        log.error("[权限不足] ", e);
//        return Result.fail(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg());
//    }

    /**
     * 12. 未登录
     */
//    @ExceptionHandler({org.apache.shiro.authc.AuthenticationException.class,
//            io.jsonwebtoken.JwtException.class})
//    public Result<?> handleUnLogin(Exception e) {
//        log.error("[未登录/Token错误] ", e);
//        return Result.fail(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg());
//    }

    /**
     * 13. 微服务调用异常（OpenFeign）
     */
//    @ExceptionHandler(feign.FeignException.class)
//    public Result<?> handleFeignException(feign.FeignException e) {
//        log.error("[微服务调用失败]{}  ");
//        return Result.fail(ResultCode.REMOTE_ERROR.getCode(), "远程服务调用失败");
//    }

    /**
     * 14. 最终兜底：所有未知异常（最重要）
     */
    @ExceptionHandler(Throwable.class)
    public Result<?> handleThrowable(Throwable e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String requestId = request.getAttribute("requestId").toString();

        // 线上绝对不能把异常抛给前端！只打日志，前端显示“服务器繁忙”
        log.error("[系统异常] uri={} requestId={}", uri, requestId, e);

        Result<?> r = Result.fail(ResultCode.SYSTEM_ERROR.getCode(), "服务器繁忙，请稍后再试");
        r.setRequestId(requestId);
        return r;
    }
}
