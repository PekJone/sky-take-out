package com.face.test.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-01  16:44
 */
@Service
public class StreamingChatService {
    private final ChatClient chatClient;
    @Autowired
    public StreamingChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    // 返回 Flux，用于 WebFlux 或 SSE 实时推送
    public Flux streamingChat(String input) {
        return chatClient.prompt()
                .user(input)
                .stream()
                .content();
    }

    // 若需要完整元数据（Token 用量等），返回 ChatResponse 流
    public Flux<ChatResponse> streamingChatWithMetadata(String input) {
        return chatClient.prompt()
                .user(input)
                .stream()
                .chatResponse();
    }

}
