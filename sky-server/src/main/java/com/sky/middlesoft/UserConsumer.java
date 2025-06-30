package com.sky.middlesoft;

import com.sky.entity.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-06-30  15:57
 */
public class UserConsumer {
    private static final String BOOTSTRAP_SERVERS = "115.190.75.32:9092,115.190.75.32:9093,115.190.75.32:9094";
    private static final String GROUP_ID = "app-consumer-group";

    public static void main(String[] args) {
        System.out.println("尝试连接到 Kafka: " + BOOTSTRAP_SERVERS);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);

        // 消费控制
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        // 性能优化
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1024);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 200);

        // 容错配置
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);

        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");

        try (KafkaConsumer<String, User> consumer = new KafkaConsumer<>(props)) {
            System.out.println("成功创建 KafkaConsumer 实例");

            consumer.subscribe(Collections.singleton("wang"));
            System.out.println("已订阅主题: wang");

            while (true) {
                System.out.println("开始 poll()...");
                ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(1000));
                System.out.println("获取到 " + records.count() + " 条记录");

                for (ConsumerRecord<String, User> record : records) {
                    System.out.printf("收到消息: offset=%d, key=%s, value=%s%n",
                            record.offset(), record.key(), record.value());
                }
            }
        } catch (Exception e) {
            System.err.println("Kafka消费者错误:");
            e.printStackTrace();
        }
    }
}