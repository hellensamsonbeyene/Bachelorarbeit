package com.chatbot.springbootchatbot.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomEntitiesLoader {

    public static List<String> loadCustomEntities(String filePath) {
        List<String> customEntities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean entitiesSection = false;

            while ((line = reader.readLine()) != null) {
                // Überprüfen, ob wir uns im Abschnitt "#Entitäten" befinden
                if (line.trim().equals("#Entitäten")) {
                    entitiesSection = true;
                    continue;
                }

                // Wenn wir im Entitäten-Abschnitt sind, fügen Sie das Wort zur Liste hinzu
                if (entitiesSection && !line.trim().isEmpty()) {
                    customEntities.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading custom entities from file: " + filePath + ". Details: " + e.getMessage());
        }

        return customEntities;
    }

    public static void main(String[] args) {
        String filePath = "springboot-chatbot/src/main/resources/custom-entities.txt"; // Ersetzen Sie dies durch den tatsächlichen Dateipfad

        List<String> customEntities = loadCustomEntities(filePath);

        // Hier können Sie die customEntities-Liste verwenden, z.B. an Ihre Chatbot-Logik übergeben
        for (String entity : customEntities) {
            System.out.println("Custom Entity: " + entity);
        }
    }
}
