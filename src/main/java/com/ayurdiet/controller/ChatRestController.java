package com.ayurdiet.controller;

import com.ayurdiet.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/chat")
    public Map<String, String> chatWithBot(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message"); // must match frontend key
        String reply = geminiService.getGeminiResponse(userMessage);
        return Map.of("reply", reply);
    }
}
