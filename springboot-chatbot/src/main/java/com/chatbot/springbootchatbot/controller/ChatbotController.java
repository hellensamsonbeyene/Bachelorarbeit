package com.chatbot.springbootchatbot.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    @PostMapping("/analyze")
    public String analyzeText(@RequestBody UserInputMessage userInputMessage) {
        String userInput = userInputMessage.userInput();
        // Führen Sie OpenNLP und NER auf userInput durch
        // Implementieren Sie Ihre Logik hier
        // Senden Sie das Ergebnis zurück an den Client
        return yourNERLogic(userInput);
    }

    private String yourNERLogic(String userInput) {
        // Fügen Sie Ihre OpenNLP/NER-Logik hier ein
        return new StringBuilder(userInput).reverse().toString();
    }
     public record UserInputMessage(String userInput) {

        @Override
        @JsonProperty("userInput")
            public String userInput() {
                return userInput;
            }
        }
}
