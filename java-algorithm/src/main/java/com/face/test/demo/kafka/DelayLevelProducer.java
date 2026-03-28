package com.face.test.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-28  16:11
 */
@Component
@RequiredArgsConstructor
public class DelayLevelProducer {
      private final KafkaTemplate<String,DelayMessageDto> kafkaTemplate;
    /**
     * 发送5s延时消息
     */
    public void sendDelay5sMessage(DelayMessageDto message) {
        kafkaTemplate.send(KafkaTopicConst.DELAY_5S, message);
    }
    /**
     * 发送30min延时消息
     */
    public void sendDelay30minMessage(DelayMessageDto message) {
        kafkaTemplate.send(KafkaTopicConst.DELAY_30MIN, message);
    }
}
