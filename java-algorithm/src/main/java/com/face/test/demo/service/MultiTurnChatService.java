package com.face.test.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-01  16:29
 */
@Service
public class MultiTurnChatService {
    // 感知器  维护对话历史
    private final List<Message> conversationHistory = new ArrayList<>();
    private final ChatClient chatClient;
    @Autowired
    MultiTurnChatService(ChatClient chatClient){
        this.chatClient = chatClient;
        //初始化 系统提示
        conversationHistory.add(new SystemMessage("你是一个贴心的生活助手，记得用户的偏好。"));
    }

    public String multiTurnChat(String userInput){
        conversationHistory.add(new UserMessage(userInput));
        String assistantResponse = chatClient.prompt()
                .messages(conversationHistory)
                .call()
                .content();
        //添加助手回复到历史
        conversationHistory.add(new AssistantMessage(assistantResponse));
        return assistantResponse;
    }

}
