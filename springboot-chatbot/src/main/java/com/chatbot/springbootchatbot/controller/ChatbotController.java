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

/**
 * ChatbotController behandelt eingehende HTTP-Anfragen im Zusammenhang mit der Chatbot-Funktionalität.
 */
@RestController
public class ChatbotController {

    /**
     * Analysiert die Benutzereingabe und gibt eine Antwort zurück.
     *
     * @param userInputMessage Die Benutzereingabemeldung, die im HTTP-body empfangen wird.
     * @return Die durch die Analyse der Benutzereingabe generierte Antwort.
     */
    @PostMapping("/analyze")
    public String analyzeText(@RequestBody UserInputMessage userInputMessage) {
        String userInput = userInputMessage.userInput();

        return AnalyzeTextFromChatbot.analyzeUserInput(userInput);
    }

    /**
     * Lädt eine Datei mit benutzerdefinierten Entitäten hoch und aktualisiert die Chatbot-Konfiguration.
     *
     * @param file Die Multipart-Datei mit benutzerdefinierten Entitäten und Standardnachricht.
     * @return ResponseEntity
     */
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
