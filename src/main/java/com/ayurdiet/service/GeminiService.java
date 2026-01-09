package com.ayurdiet.service;

import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    //private final ObjectMapper mapper = new ObjectMapper();

    public String getGeminiResponse(String userMessage) {
        try {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

            // 🟢 Add style instruction for short, pointwise answers
        String systemInstruction = "You are an Ayurveda chatbot. " +
        "Respond in a friendly, conversational tone. " +
        "Keep answers short (2–4 sentences) when possible. " +
        "If the user’s question asks for remedies, foods, steps, or comparisons, " +
        "then format the response in clean HTML with <ul>, <li>, <b>, <p>, etc. " +
        "Do not include <html> or <body>, just return snippet HTML. " +
        "Always stay focused on Ayurveda and wellness.";



            Map<String, Object> request = new HashMap<>();
            request.put("contents", new Object[]{
                    Map.of("parts", new Object[]{
                            Map.of("text", systemInstruction + "\n\nUser: " + userMessage)
                    })
            });

            JsonNode response = restTemplate.postForObject(url, request, JsonNode.class);
            if (response != null &&
                response.has("candidates") &&
                response.get("candidates").isArray() &&
                response.get("candidates").size() > 0) {
                return response.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
            }
        } catch (Exception e) {
            return "⚠️ Error calling Gemini API: " + e.getMessage();
        }
        return "⚠️ No response from Gemini API.";
    }
}
