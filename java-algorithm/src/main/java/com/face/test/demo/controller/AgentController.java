package com.face.test.demo.controller;

import com.face.test.demo.service.ChatAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-01  15:53
 */
@RestController
@RequestMapping("/agent")
public class AgentController {

    private final ChatAgent chatAgent;
    @Autowired
    public AgentController(ChatAgent chatAgent) {
        this.chatAgent = chatAgent;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String input){
        return chatAgent.process(input);
    }
}
