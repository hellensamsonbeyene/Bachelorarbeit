package com.chatbot.springbootchatbot.util;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StanfordNLP {

    private static Map<String, NameFinderME> entityFinders;

    static {
        try {
            entityFinders = loadEntityFinders();
        } catch (IOException e) {
            // Handle IOException appropriately
            e.printStackTrace();
        }
    }
    public StanfordNLP() throws IOException {
        entityFinders = loadEntityFinders();
    }

    private static Map<String, NameFinderME> loadEntityFinders() throws IOException {
        Map<String, NameFinderME> finders = new HashMap<>();

        List<String> entityLines = Files.readAllLines(Paths.get("springboot-chatbot/src/main/resources/TrainingData.txt"));
        System.out.println("FILES"+entityLines);

        for (String entityLine : entityLines) {
            System.out.println("ENTITY"+entityLine);
            if (entityLine.startsWith("[Entitäten]")) {
                continue;
            }
            if (entityLine.startsWith("[")) {

                break;
            }

            String entity = entityLine.toLowerCase().trim();
            String modelPath = "models/" + entity + ".bin";
            finders.put(entity, createNameFinder(modelPath));

        }

        System.out.println("FINDERS" + finders);

        return finders;
    }
    private static NameFinderME createNameFinder(String modelPath) throws IOException {
        try (InputStream modelIn = StanfordNLP.class.getClassLoader().getResourceAsStream(modelPath)) {
            assert modelIn != null;
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            return new NameFinderME(model);
        }
    }

    public static String processUserInput(String userInput) {
        Map<String, String> recognizedEntities = new HashMap<>();

        for (Map.Entry<String, NameFinderME> entry : entityFinders.entrySet()) {
            String entity = entry.getKey();
            NameFinderME finder = entry.getValue();

            Span[] spans = finder.find(userInput.split(" "));
            if (spans.length > 0) {
                String recognizedEntity = String.join(" ", Arrays.copyOfRange(userInput.split(" "), spans[0].getStart(), spans[0].getEnd()));
                recognizedEntities.put(entity, recognizedEntity);
            }
        }

        String response = generateResponse(recognizedEntities);
        return response.isEmpty() ? "Ich konnte keine Entitäten erkennen." : response;
    }

    private static String generateResponse(Map<String, String> recognizedEntities) {
        // Implementieren Sie die Logik zum Generieren der Antwort basierend auf den erkannten Entitäten
        // Verwenden Sie die Map recognizedEntities, um auf die erkannten Entitäten zuzugreifen

        StringBuilder responseBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : recognizedEntities.entrySet()) {
            responseBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return responseBuilder.toString();
    }
}
