package com.face.test.demo.kafka;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-28  16:09
 */
public class KafkaTopicConst {
    // 方案1
    public static final String DELAY_5S = "delay-5s-topic";
    public static final String DELAY_30MIN = "delay-30min-topic";
    public static final String ORDER_CLOSE = "order-close-topic";

    // 方案2
    public static final String DELAY_GENERAL = "delay-general-topic";

    // 方案3 Redis + Kafka
    public static final String DELAY_REDIS_FORWARD = "delay-redis-forward-topic";
}
