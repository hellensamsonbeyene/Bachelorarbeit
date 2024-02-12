package com.chatbot.springbootchatbot.controller;

import com.chatbot.springbootchatbot.logic.AnalyzeTextFromChatbot;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    @PostMapping("/analyze")
    public String analyzeText(@RequestBody UserInputMessage userInputMessage) {
        String userInput = userInputMessage.userInput();

        return AnalyzeTextFromChatbot.analyzeUserInput(userInput);

    }

    public record UserInputMessage(String userInput) {
        @Override
        @JsonProperty("userInput")
        public String userInput() {
            return userInput;
        }
    }
}
