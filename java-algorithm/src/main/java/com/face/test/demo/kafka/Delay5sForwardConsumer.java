package com.face.test.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-28  16:18
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class Delay5sForwardConsumer {

    private final KafkaTemplate<String,DelayMessageDto> kafkaTemplate;
    @KafkaListener(topics = KafkaTopicConst.DELAY_5S, groupId = "delay-group")
    public void consume(ConsumerRecord<String,DelayMessageDto> record) throws InterruptedException {
        DelayMessageDto message = record.value();
        log.info("5s延时队列收到消息，开始sleep: {}", message);

        // 固定延时
        TimeUnit.SECONDS.sleep(5);
        // 转发到真正业务topic
        kafkaTemplate.send(KafkaTopicConst.ORDER_CLOSE, message.getOrderId(), message);
        log.info("已转发至订单关闭队列: {}", message.getOrderId());

    }

}
