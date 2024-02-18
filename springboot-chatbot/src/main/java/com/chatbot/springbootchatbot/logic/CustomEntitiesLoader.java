package com.chatbot.springbootchatbot.logic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomEntitiesLoader {

    private static final String filePath = "springboot-chatbot/src/main/resources/custom-entities.txt";
    private static Map<String, String> customEntities;
    private static String standardMessage;

    public static Map<String, String> getCustomEntities() {
        return customEntities;
    }

    public static String getStandardMessage() {
        return standardMessage;
    }

    @PostConstruct
    public ResponseEntity<String> init() {
        customEntities = loadCustomEntities(filePath);
        standardMessage = loadStandardMessage(filePath);

        if (customEntities.isEmpty() || standardMessage.isEmpty()) {
            return new ResponseEntity<>("Die Liste der benutzerdefinierten Entitäten oder die Standardnachricht ist leer oder die Datei existiert nicht.", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            for (Map.Entry<String, String> entry : customEntities.entrySet()) {
                System.out.println("Benutzerdefinierte Entität: " + entry.getKey() + ", Satz: " + entry.getValue());
            }
        }

        // Adjust the return statement based on your requirements
        return new ResponseEntity<>("Erfolgreich initialisiert.", HttpStatus.OK);
    }

    public static ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            saveFile(filePath, file);

            // Reload custom entities after saving the file
            customEntities = loadCustomEntities(filePath);
            standardMessage = loadStandardMessage(filePath);

            if (customEntities.isEmpty()) {
                return new ResponseEntity<>("Die Datei ist leer oder die Datei existiert nicht.", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                // Print each custom entity and its corresponding sentence
                for (Map.Entry<String, String> entry : customEntities.entrySet()) {
                    System.out.println("Benutzerdefinierte Entität: " + entry.getKey() + ", Satz: " + entry.getValue());
                }

                // Adjust the return statement based on your requirements
                return new ResponseEntity<>("Datei erfolgreich hochgeladen.", HttpStatus.OK);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Hochladen der Datei: " + e.getMessage());
            return new ResponseEntity<>("Fehler beim Hochladen der Datei: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void saveFile(String filePath, MultipartFile file) throws IOException {
        // Use Paths.get to create a Path object from the specified file path
        Path path = Paths.get(filePath);

        // Use try-with-resources to automatically close InputStream
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(path.toFile())) {

            // Copy the contents of the uploaded file to the specified path
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
    public static String loadStandardMessage(String filePath) {
        standardMessage = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean standardMessageSection = false;

            while ((line = reader.readLine()) != null) {
                // Check if we are in the "#Standardnachricht" section
                if (line.trim().equals("#Standardnachricht")) {
                    standardMessageSection = true;
                    continue;
                }

                // If we are in the standard message section, save the message
                if (standardMessageSection && !line.trim().isEmpty()) {
                    standardMessage = line.trim();
                    break;  // Exit the loop after finding the standard message
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Standardnachricht aus der Datei: " + filePath + ". Details: " + e.getMessage());
        }

        return standardMessage;
    }
    public static Map<String, String> loadCustomEntities(String filePath) {
        customEntities = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean entitiesSection = false;
            String currentEntity;

            while ((line = reader.readLine()) != null) {
                // Überprüfen, ob wir im Abschnitt "#Entitäten" sind
                if (line.trim().equals("#Entitäten")) {
                    entitiesSection = true;
                    continue;
                }

                // Wenn wir uns im Entitäten-Abschnitt befinden, Entitäten und Sätze analysieren
                if (entitiesSection && !line.trim().isEmpty()) {
                    String[] parts = line.split(":", 2); // Am ersten Doppelpunkt aufteilen
                    if (parts.length == 2) {
                        currentEntity = parts[0].trim();
                        String satz = parts[1].trim();
                        customEntities.put(currentEntity, satz);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden benutzerdefinierter Entitäten aus der Datei: " + filePath + ". Details: " + e.getMessage());
        }

        return customEntities;
    }


}
