package com.sky.middlesoft;

import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-06-28  9:30
 */
public class SimpleProducer {
    private static final String BOOTSTRAP_SERVERS = "115.190.75.32:9092,115.190.75.32:9093,115.190.75.32:9094";
    private static final String TOPIC_NAME = "test";
    private static final String GROUP_ID = "app-consumer-group";

    public static void main(String[] args) {
        Properties props = new Properties();
        // 基础配置
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

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 优雅关闭处理
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down consumer...");
            consumer.wakeup();
        }));

        try {
            // 订阅主题（支持正则表达式）
            consumer.subscribe(Pattern.compile("test|important.*"),
                    new ConsumerRebalanceListener() {
                        @Override
                        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                            handleRebalanceBefore(partitions);
                        }

                        @Override
                        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                            initializeState(partitions);
                        }
                    });

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                // 按分区处理保证顺序
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);

                    try {
                        for (ConsumerRecord<String, String> record : partitionRecords) {
                            processRecord(record);
                        }

                        // 按分区提交offset
                        long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                        consumer.commitSync(Collections.singletonMap(
                                partition, new OffsetAndMetadata(lastOffset + 1)));
                    } catch (Exception e) {
                        handleProcessingFailure(partition, e);
                    }
                }
            }
        } catch (WakeupException e) {
            // 忽略关闭信号
        } catch (Exception e) {
            handleFatalError(e);
        } finally {
            try {
                consumer.commitSync();
            } finally {
                consumer.close();
                System.out.println("Consumer closed");
            }
        }
    }

    private static void processRecord(ConsumerRecord<String, String> record) {
        System.out.printf("Processing [%s] key=%s, value=%s%n",
                record.topic(), record.key(), record.value());

        // 业务处理逻辑
        try {
            // 模拟处理耗时
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void handleRebalanceBefore(Collection<TopicPartition> partitions) {
//        System.out.println("Rebalance triggered. Revoked partitions: " + partitions);
        // 可以在这里保存处理状态或清理资源
    }

    private static void initializeState(Collection<TopicPartition> partitions) {
//        System.out.println("Assigned new partitions: " + partitions);
        // 初始化分区处理状态
    }

    private static void handleProcessingFailure(TopicPartition partition, Exception e) {
//        System.err.printf("Failed to process partition %s: %s%n", partition, e.getMessage());
        // 可以在这里实现死信队列等容错机制
    }

    private static void handleFatalError(Exception e) {
//        System.err.println("Fatal error in consumer: " + e.getMessage());
        // 报警通知等处理
    }
}