package com.chatbot.springbootchatbot.logic;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalyzeTextFromChatbot {
    static Tokenizer tokenizer;

    static {
        // Load the tokenizer model
        try (InputStream modelIn = new FileInputStream("springboot-chatbot/src/main/resources/opennlp-de-ud-gsd-tokens-1.0-1.9.3.bin")) {
            TokenizerModel model = new TokenizerModel(modelIn);
            tokenizer = new TokenizerME(model);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Initialisieren des Tokenizers", e);
        }
    }

    public static String analyzeUserInput(String userInput) {
        List<String> customEntities = CustomEntitiesLoader.getCustomEntities();
        String[] tokens = tokenizer.tokenize(userInput);
        System.out.println(Arrays.toString(tokens));
        List<String> recognizedWords = new ArrayList<>();

        for (String token : tokens) {
            if (customEntities.contains(token)) {
                recognizedWords.add(token);
            }
        }

        return "Erkannte Entitäten:" + String.join(", ", recognizedWords);
    }
}
