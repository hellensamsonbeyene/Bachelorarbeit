package com.chatbot.springbootchatbot.controller;

import com.chatbot.springbootchatbot.logic.AnalyzeTextFromChatbot;
import com.chatbot.springbootchatbot.logic.CustomEntitiesLoader;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ChatbotController {

    @PostMapping("/analyze")
    public String analyzeText(@RequestBody UserInputMessage userInputMessage) {
        String userInput = userInputMessage.userInput();

        return AnalyzeTextFromChatbot.analyzeUserInput(userInput);

    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        return CustomEntitiesLoader.uploadFile(file);
    }

    public record UserInputMessage(String userInput) {
        @Override
        @JsonProperty("userInput")
        public String userInput() {
            return userInput;
        }
    }
}
