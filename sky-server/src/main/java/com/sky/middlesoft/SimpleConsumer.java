package com.sky.middlesoft;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.awt.datatransfer.StringSelection;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-06-28  9:31
 */
public class SimpleConsumer {
    private static final String BOOTSTRAP_SERVERS = "115.190.75.32:9092,115.190.75.32:9093,115.190.75.32:9094";
    private static final String TOPIC_NAME = "test";

    public static void main(String[] args) {
        Properties props = new Properties();
        //基础配置
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //高可用性配置

        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "prod-1"); // 事务支持
        props.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);

        // 性能优化
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 20);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768);

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        try {
            // 初始化事务
            producer.initTransactions();

            for (int i = 100; i < 10000; i++) {
                producer.beginTransaction();
                try {
                    // 发送业务消息
                    ProducerRecord<String, String> record =
                            new ProducerRecord<>(TOPIC_NAME, "key-" + i, "value-" + i);

                    // 添加消息头
                    record.headers().add("trace-id", UUID.randomUUID().toString().getBytes());

                    // 同步发送关键消息
                    if (i % 10 == 0) {
                        Future<RecordMetadata> future = producer.send(record);
                        RecordMetadata metadata = future.get();
                        logDelivery(metadata, record);
                    }
                    // 异步发送普通消息
                    else {
                        producer.send(record, (metadata, exception) -> {
                            if (exception != null) {
                                handleFailure(record, exception);
                            } else {
                                logDelivery(metadata, record);
                            }
                        });
                    }

                    // 提交事务
                    producer.commitTransaction();
                } catch (Exception e) {
                    producer.abortTransaction();
                    handleTransactionFailure(e);
                }
            }
        } finally {
            producer.close(Duration.ofSeconds(30));
        }
    }

    private static void logDelivery(RecordMetadata metadata, ProducerRecord<?, ?> record) {
        System.out.printf("Delivered %s to %s-%d@%d%n",
                record.key(), metadata.topic(), metadata.partition(), metadata.offset());
    }

    private static void handleFailure(ProducerRecord<?, ?> record, Exception ex) {
        System.err.printf("Failed to send %s: %s%n", record.key(), ex.getMessage());
    }

    private static void handleTransactionFailure(Exception ex) {
        System.err.printf("Transaction failed: %s%n", ex.getMessage());
    }

    }




