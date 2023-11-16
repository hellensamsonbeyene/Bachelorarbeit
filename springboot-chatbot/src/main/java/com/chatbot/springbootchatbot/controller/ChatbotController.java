package com.chatbot.springbootchatbot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    @PostMapping("/analyze")
    public String analyzeText(@RequestBody String userInput) {
        // Führen Sie OpenNLP und NER auf userInput durch
        // Implementieren Sie Ihre Logik hier
        String nerResult = yourNERLogic(userInput);
        System.out.println(nerResult);
        // Senden Sie das Ergebnis zurück an den Client
        return nerResult;
    }

    // Implementieren Sie Ihre OpenNLP/NER-Logik hier
    private String yourNERLogic(String userInput) {
        // Fügen Sie Ihre OpenNLP/NER-Logik hier ein
        return "NER result for: " + userInput;
    }
}
