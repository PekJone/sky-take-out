package com.sky.middlesoft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.entity.User;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-06-30  15:42
 */
public class DeserializeUser implements Deserializer<User> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public User deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes,User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public User deserialize(String topic, Headers headers, ByteBuffer data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
