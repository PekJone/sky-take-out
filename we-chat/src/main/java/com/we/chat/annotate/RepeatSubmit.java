package com.we.chat.annotate;

import java.lang.annotation.*;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  16:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
        /**
        * 过期时间，单位秒
         * 默认2秒内重复提交会被拦截
        */
        int expire() default 2;
}
