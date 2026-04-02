package com.face.test.demo.service;

import com.face.test.demo.util.WeatherUtilTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-01  15:25
 */
@Service
public class ChatAgent {
    private final ChatClient chatClient;
    private final WeatherUtilTool weatherUtilTool;

    private final List<Message> conversionHistory = new ArrayList<>();

    public ChatAgent(ChatClient chatClient ,WeatherUtilTool weatherUtilTool){
           this.chatClient = chatClient;
           this.weatherUtilTool = weatherUtilTool;
           conversionHistory.add(new SystemMessage(
                "你是一个智能助手。当用户询问天气时，调用天气工具；否则直接回答。"
        ));
    }

    public String process(String input){
        conversionHistory.add(new UserMessage(input));
        String response ;
        if(input.contains("天气")){
            String city = extractCity(input);
            String weather = weatherUtilTool.queryWeather(city);
            response = String.format("%s的天气：%s", city, weather);
        }else{
            Prompt prompt = new Prompt(conversionHistory);
            response = chatClient.prompt().user(input).call().content();
        }
        conversionHistory.add(new AssistantMessage(response));
        return response;
    }



    private String extractCity(String input) {
        String[] cities = {"北京", "上海", "深圳"};
        for (String city : cities) {
            if (input.contains(city)) return city;
        }
        return "北京";
    }
}
