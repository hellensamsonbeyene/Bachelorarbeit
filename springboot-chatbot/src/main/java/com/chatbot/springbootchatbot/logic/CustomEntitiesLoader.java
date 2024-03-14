package com.chatbot.springbootchatbot.logic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;

/**
 * CustomEntitiesLoader ist für das Laden und Verwalten benutzerdefinierter Entitäten aus einer Textdatei verantwortlich
 */
@Component
public class CustomEntitiesLoader {
    private static final String filePath = "springboot-chatbot/src/main/resources/custom-entities.txt";
    public static final String initialFilePath = "springboot-chatbot/src/main/resources/example-entities.txt";

    public static List<Map.Entry<String, String>> customEntities;
    public static String standardMessage;

    public static List<Map.Entry<String, String>> getCustomEntities() {
        return customEntities;
    }

    public static String getStandardMessage() {
        return standardMessage;
    }

    /**
     * Initialisieren und Laden benutzerdefinierter Entitäten aus der Textdatei.
     *
     * @return ResponseEntity
     */
    @PostConstruct
    public ResponseEntity<String> init() throws IOException {
        //Überprüfen, ob Stoppwörter in den Entitäten existieren
        customEntities = loadCustomEntities(initialFilePath);
        standardMessage = loadStandardMessage(initialFilePath);

        if (customEntities.isEmpty() || standardMessage.isEmpty()) {
            return new ResponseEntity<>("Die Liste der benutzerdefinierten Entitäten oder die Standardnachricht ist leer oder die Datei existiert nicht.", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            for (Map.Entry<String, String> entry : customEntities) {
                System.out.println("Benutzerdefinierte Entität: " + entry.getKey() + ", Satz: " + entry.getValue());
            }
        }
        return new ResponseEntity<>("Erfolgreich initialisiert.", HttpStatus.OK);
    }

    /**
     * Hochladen einer Datei, Speichern und erneuten Laden von benutzerdefinierten Entitäten und der Standardnachricht.
     *
     * @param file Die hochzuladende Datei.
     * @return ResponseEntity
     */
    public static ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return new ResponseEntity<>("Die hochgeladene Datei ist leer.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            saveFile(filePath, file);
            List<Map.Entry<String, String>> loadedCustomEntities = loadCustomEntities(filePath);
            String loadedStandardMessage = loadStandardMessage(filePath);

            if (loadedCustomEntities.isEmpty() || loadedStandardMessage.isEmpty()) {
                customEntities = loadCustomEntities(filePath);
                standardMessage = loadStandardMessage(filePath);

                return new ResponseEntity<>("Die Standardnachricht oder die Entitäten sind leer. Bitte lade eine neue Datei hoch.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //Überprüfen, ob Stoppwörter in den Entitäten existieren
            StopwordsLoader.checkForStopwords(loadedCustomEntities);
            // Wenn alles erfolgreich war, aktualisiere die Werte
            customEntities = loadedCustomEntities;
            standardMessage = loadedStandardMessage;

            for (Map.Entry<String, String> entry : customEntities) {
                System.out.println("Benutzerdefinierte Entität: " + entry.getKey() + ", Satz: " + entry.getValue());
            }

            return new ResponseEntity<>("Datei erfolgreich hochgeladen.", HttpStatus.OK);
        } catch (IOException e) {
            System.err.println("Fehler beim Hochladen der Datei: " + e.getMessage());
            return new ResponseEntity<>("Fehler beim Hochladen der Datei: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Speichern einer Datei an einem bestimmten Pfad.
     *
     * @param filePath Der Pfad, an dem die Datei gespeichert werden soll.
     * @param file     Die zu speichernde Datei.
     * @throws IOException Falls ein Fehler beim Speichern der Datei auftritt.
     */
    public static void saveFile(String filePath, MultipartFile file) throws IOException {
        Path path = Paths.get(filePath);

        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(path.toFile())) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Datei erfolgreich gespeichert unter: " + filePath);
        } catch (IOException e) {
            throw new IOException("Fehler beim Speichern der Datei: " + e.getMessage());
        }
    }

    /**
     * Laden der Standardnachricht aus der Textdatei.
     *
     * @param filePath Der Pfad zur Textdatei.
     * @return Die geladene Standardnachricht.
     */
    public static String loadStandardMessage(String filePath) {
        standardMessage = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean standardMessageSection = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("#Standardnachricht")) {
                    standardMessageSection = true;
                    continue;
                }

                if (standardMessageSection && !line.trim().isEmpty()) {
                    standardMessage = line.trim();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Standardnachricht aus der Datei: " + filePath + ". Details: " + e.getMessage());
        }

        return standardMessage;
    }

    /**
     * Laden benutzerdefinierter Entitäten aus der Textdatei
     *
     * @param filePath Der Pfad zur Textdatei.
     * @return Liste benutzerdefinierter Entitäten als Schlüssel-Wert-Paare.
     */
    public static List<Map.Entry<String, String>> loadCustomEntities(String filePath) throws IOException {
        List<Map.Entry<String, String>> customEntitiesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean entitiesSection = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("#Entitäten")) {
                    entitiesSection = true;
                    continue;
                }

                if (entitiesSection && !line.trim().isEmpty()) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        String currentEntity = parts[0].trim().toLowerCase();
                        String sentence = parts[1].trim();
                        // Überprüfung auf Stoppwörter

                        customEntitiesList.add(new AbstractMap.SimpleEntry<>(currentEntity, sentence));
                        System.out.println("Entität " + currentEntity + ", Satz: " + sentence);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden benutzerdefinierter Entitäten aus der Datei: " + filePath + ". Details: " + e.getMessage());
        }
        return customEntitiesList;
    }
}
