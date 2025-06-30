package com.sky.middlesoft;

import com.sky.entity.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-06-30  15:44
 */
public class UserProducer {
    private static final String BOOTSTRAP_SERVERS = "115.190.75.32:9092,115.190.75.32:9093,115.190.75.32:9094";
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"com.sky.middlesoft.SerializeUser");
        KafkaProducer<String, User> producer = new KafkaProducer<>(properties);

        User user = new User();

        user.setName("wang4");
        user.setOpenid("54543254352435434");
        user.setId(20000L);
        user.setSex("1");

        ProducerRecord<String,User> record = new ProducerRecord<>("wang",user);

        producer.send(record);
        producer.close();
        System.out.println("User send:"+user.getName());


    }
}
