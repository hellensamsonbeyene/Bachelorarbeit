package com.chatbot.springbootchatbot.logic;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Chatbot {

    private static NameFinderME nameFinder;

    public Chatbot() {
        // Laden Sie das OpenNLP-Modell für Named Entity Recognition
        try (InputStream modelIn = new FileInputStream("path/to/en-ner-person.bin")) {
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            nameFinder = new NameFinderME(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String processUserInput(String userInput) {
        // Tokenisieren Sie die Benutzereingabe (vereinfacht für dieses Beispiel)
        String[] tokens = userInput.split(" ");

        // Erkennen Sie Named Entities in den Token
        Span[] nameSpans = nameFinder.find(tokens);

        // Extrahieren Sie die erkannten Entitäten
        StringBuilder detectedEntities = new StringBuilder();
        for (Span span : nameSpans) {
            for (int i = span.getStart(); i < span.getEnd(); i++) {
                detectedEntities.append(tokens[i]).append(" ");
            }
        }

        // Fügen Sie Ihre Chatbot-Logik hier ein

        return "Erkannte Entitäten: " + detectedEntities.toString().trim();
    }
}

