package com.chatbot.springbootchatbot.logic;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * AnalyzeTextFromChatbot ist verantwortlich für die Analyse von Benutzereingaben mithilfe von OpenNLP
 */
public class AnalyzeTextFromChatbot {

    static Tokenizer tokenizer;

    static {
        try (InputStream modelIn = new FileInputStream("springboot-chatbot/src/main/resources/opennlp-de-ud-gsd-tokens-1.0-1.9.3.bin")) {
            TokenizerModel model = new TokenizerModel(modelIn);
            tokenizer = new TokenizerME(model);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Initialisieren des Tokenizers", e);
        }
    }

    /**
     * Analysiert die Benutzereingabe und gibt den Satz der höchst priorisierten erkannten Entität zurück.
     *
     * @param userInput Die Eingabe des Benutzers, die analysiert werden soll.
     * @return Der Satz der höchst priorisierten erkannten Entität oder die Standardnachricht, falls keine Entität erkannt wurde.
     */
    public static String analyzeUserInput(String userInput) {

        List<Map.Entry<String, String>> customEntities = CustomEntitiesLoader.getCustomEntities();
        String[] tokens = tokenizer.tokenize(userInput);
        String highestPrioritySentence = null;
        //Prüfen der Rechtschreibung der Tokens und Filtern der Stoppwörter
        String[] processedTokens = ProcessTokens.processTokens(tokens,customEntities);
        System.out.println("Processed Tokens: " + Arrays.toString(processedTokens));
        // Durchlaufen der Tokens und benutzerdefinierten Entitäten
        for (String token : processedTokens) {
            for (Map.Entry<String, String> entry : customEntities) {
                String entity = entry.getKey();
                if (entity.startsWith(token)) {
                    System.out.println("Erkannte Entität: " + entity + " und Token: " + token);
                    String sentence = entry.getValue();
                    // Überprüfen und aktualisieren der höchst priorisierten Entität
                    if (highestPrioritySentence == null || customEntities.indexOf(entry) < customEntities.indexOf(getEntryByValue(customEntities, highestPrioritySentence))) {
                        highestPrioritySentence = sentence;
                    }
                    break;
                }
            }
        }

        if (highestPrioritySentence != null) {
            return highestPrioritySentence;
        } else {
            return CustomEntitiesLoader.getStandardMessage();
        }
    }

    /**
     * Hilfsmethode zum Finden eines Eintrags in einer Liste von Einträgen anhand des Werts.
     *
     * @param entries Die Liste von Einträgen, in der nach dem Wert gesucht wird.
     * @param value   Der gesuchte Wert.
     * @return Der gefundene Eintrag oder null, falls kein Eintrag mit dem Wert gefunden wurde.
     */
    private static <K, V> Map.Entry<K, V> getEntryByValue(List<Map.Entry<K, V>> entries, V value) {
        for (Map.Entry<K, V> entry : entries) {
            if (entry.getValue().equals(value)) {
                return entry;
            }
        }
        return null;
    }
}
