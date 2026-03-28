package com.face.test.demo.kafka;

import lombok.Data;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-28  16:07
 */
@Data
public class DelayMessageDto {
    private String orderId ;
    private String content;

    private long executeTime; // 执行时间戳
}


