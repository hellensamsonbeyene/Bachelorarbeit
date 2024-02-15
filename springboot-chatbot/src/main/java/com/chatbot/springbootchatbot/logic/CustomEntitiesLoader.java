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

@Component
public class CustomEntitiesLoader {

    private static final String filePath = "springboot-chatbot/src/main/resources/custom-entities.txt";
    private static List<String> customEntities;

    public static List<String> getCustomEntities() {
        return customEntities;
    }

    @PostConstruct
    public ResponseEntity<String> init() {
        customEntities = loadCustomEntities(filePath);
        // Print or use customEntities as needed
        if (customEntities.isEmpty()) {
            return new ResponseEntity<>("Die Liste der benutzerdefinierten Entitäten ist leer oder die Datei existiert nicht.", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            for (String entity : customEntities) {
                System.out.println("Benutzerdefinierte Entität: " + entity);
            }
        }
        return null;
    }

    public static ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            customEntities = loadCustomEntities(filePath);
            saveFile(filePath, file);

            if (customEntities.isEmpty()) {
                return new ResponseEntity<>("Die Datei ist leer oder die Datei existiert nicht.", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                for (String entity : customEntities) {
                    System.out.println("Benutzerdefinierte Entität: " + entity);
                    return new ResponseEntity<>("Datei erfolgreich hochgeladen.", HttpStatus.OK);

                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("Fehler beim Hochladen der Datei: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
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


    public static List<String> loadCustomEntities(String filePath) {
        customEntities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean entitiesSection = false;

            while ((line = reader.readLine()) != null) {
                // Check if we are in the "#Entitäten" section
                if (line.trim().equals("#Entitäten")) {
                    entitiesSection = true;
                    continue;
                }

                // If we are in the entities section, add the word to the list
                if (entitiesSection && !line.trim().isEmpty()) {
                    customEntities.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden benutzerdefinierter Entitäten aus der Datei: " + filePath + ". Details: " + e.getMessage());
        }

        return customEntities;
    }
}
