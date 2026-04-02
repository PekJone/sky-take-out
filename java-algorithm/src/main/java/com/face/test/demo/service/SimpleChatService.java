package com.face.test.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-01  16:21
 */
@Service
public class SimpleChatService {

    private final ChatClient chatClient;
    @Autowired
    public SimpleChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    //直接传入String 快速获取回复
    public String simpleChat(String input){
        return chatClient.prompt().user(input).call().content();
    }

    //传入message列表  获取更精确的控制
    public String chatWithSystemPrompt(String input){
        return chatClient.prompt()
                .system("\"你是一个专业的 Java 技术专家，回答请简洁、有代码示例。\"")
                .user(input)
                .call()
                .content();
    }

}
