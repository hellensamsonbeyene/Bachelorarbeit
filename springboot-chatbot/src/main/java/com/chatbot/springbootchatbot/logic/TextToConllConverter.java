package com.chatbot.springbootchatbot.logic;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class TextToConllConverter {
    public static void main(String[] args) {
        // Erstellen Sie eine Eigenschaftenklasse für die Konfiguration
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,ner");

        // Erstellen Sie eine CoreNLP-Pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Pfad zur Datei mit den Sätzen
        String inputFilePath = "src/main/resources/TrainingData.txt";

        // Pfad zur Ausgabedatei für die Trainingsdaten im CoNLL-Format
        String outputFilePath = "src/main/resources/outputExample.conll";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Erstellen Sie eine Annotation mit dem aktuellen Satz
                Annotation document = new Annotation(line);

                // Führen Sie die Pipeline aus
                pipeline.annotate(document);

                // Schreiben Sie die Ausgabe im CoNLL-Format in die Datei
                List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
                for (CoreMap sentence : sentences) {
                    for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                        String word = token.get(CoreAnnotations.TextAnnotation.class);
                        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                        String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                        // Schreiben Sie die Ausgabe im CoNLL-Format in die Datei
                        writer.write(String.format("%s\t%s\tO\t%s%n", word, pos, ner));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
