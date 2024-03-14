package com.chatbot.springbootchatbot.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StopwordsLoader {

    private static final Set<String> stopwords;

    static {
        // Stopwörter einmalig beim Starten der Klasse laden
        stopwords = loadStopwords();
    }

    private static Set<String> loadStopwords() {
        Set<String> stopwords = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("springboot-chatbot/src/main/resources/stopwords.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Stoppwörter: " + e.getMessage());
        }
        return stopwords;
    }

    /**
     * Bereinigt die übergebenen Tokens von Stopwörtern und gibt das bereinigte Array zurück.
     *
     * @param tokens Ein Array von Tokens, das bereinigt werden soll.
     * @return Ein bereinigtes Array von Tokens ohne Stopwörter.
     */
    public static String[] cleanTokens(String[] tokens) {
        List<String> cleanedTokens = new ArrayList<>();

        for (String token : tokens) {
            // Füge das Token zur bereinigten Liste hinzu, falls es kein Stopwort ist
            if (!stopwords.contains(token.toLowerCase())) {
                cleanedTokens.add(token);
            }
        }
        return cleanedTokens.toArray(new String[0]);
    }

    /**
     * Überprüft, ob in den benutzerdefinierten Entitäten Stoppwörter als Schlüssel existieren.
     *
     * @param customEntitiesList Die Liste der benutzerdefinierten Entitäten.
     * @throws IOException Falls Stoppwörter gefunden werden.
     */
    public static void checkForStopwords(List<Map.Entry<String, String>> customEntitiesList) throws IOException {
        List<String> foundStopwords = new ArrayList<>();

        // Durchlaufen der benutzerdefinierten Entitäten
        for (Map.Entry<String, String> customEntity : customEntitiesList) {
            String entityKey = customEntity.getKey();
            // Überprüfen, ob die benutzerdefinierte Entität ein Stoppwort ist
            if (stopwords.contains(entityKey.toLowerCase())) {
                foundStopwords.add(entityKey);
            }
        }

        // Wenn Stoppwörter gefunden wurden, eine IOException werfen
        if (!foundStopwords.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Stoppwörter gefunden: ");
            for (String stopword : foundStopwords) {
                errorMessage.append(stopword).append(", ");
            }
            errorMessage.deleteCharAt(errorMessage.length() - 1); // Remove last comma
            errorMessage.deleteCharAt(errorMessage.length() - 1); // Remove space
            throw new IOException(errorMessage.toString());
        }
    }
}
