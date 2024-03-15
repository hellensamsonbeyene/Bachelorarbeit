package com.chatbot.springbootchatbot.logic;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CustomEntitiesLoaderTest {
    private static final String CUSTOM_FILE_PATH = "src/main/resources/custom-entities.txt";

    @Test
    void init_LoadsCustomEntitiesAndStandardMessage_Success() throws IOException {
        CustomEntitiesLoader customEntitiesLoader = new CustomEntitiesLoader();
        ResponseEntity<String> responseEntity = customEntitiesLoader.init();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(CustomEntitiesLoader.getCustomEntities());
        assertNotNull(CustomEntitiesLoader.getStandardMessage());
    }

    @Test
    void init_CustomEntitiesFileNotFound_ReturnsInternalServerError() throws IOException {
        CustomEntitiesLoader customEntitiesLoader = new CustomEntitiesLoader();
        CustomEntitiesLoader.initialFilePath = "non_existing_file.txt";
        ResponseEntity<String> responseEntity = customEntitiesLoader.init();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void loadCustomEntities_LoadsCustomEntitiesFromFile_Success() throws IOException {
        List<Map.Entry<String, String>> customEntities = CustomEntitiesLoader.loadCustomEntities(new File(CUSTOM_FILE_PATH).getAbsolutePath());

        assertNotNull(customEntities);
        assertFalse(customEntities.isEmpty());
    }

    @Test
    void loadStandardMessage_LoadsStandardMessageFromFile_Success() {
        String standardMessage = CustomEntitiesLoader.loadStandardMessage(new File(CUSTOM_FILE_PATH).getAbsolutePath());

        assertNotNull(standardMessage);
        assertFalse(standardMessage.isEmpty());
    }

    @Test
    void uploadFile_WithValidFile_UpdatesCustomEntitiesAndStandardMessage() {
        CustomEntitiesLoader.customEntities = null;
        CustomEntitiesLoader.standardMessage = null;
        MockMultipartFile file = new MockMultipartFile("file", "custom-entities.txt", "text/plain", "#Standardnachricht\n Sentence\n\n#Entitäten\n entity:sentence.".getBytes());

        ResponseEntity<String> responseEntity = CustomEntitiesLoader.uploadFile(file);
        System.out.println("test"+ responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(CustomEntitiesLoader.getCustomEntities());
        assertNotNull(CustomEntitiesLoader.getStandardMessage());
    }

    @Test
    void uploadFile_WithEmptyFile_ReturnsInternalServerError() {
        MockMultipartFile file = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);

        ResponseEntity<String> responseEntity = CustomEntitiesLoader.uploadFile(file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void uploadFile_WithInvalidFile_ReturnsInternalServerError() {
        MockMultipartFile file = new MockMultipartFile("file", "invalid.txt", "text/plain", "invalid content".getBytes());

        ResponseEntity<String> responseEntity = CustomEntitiesLoader.uploadFile(file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void loadStandardMessage_FileIsEmpty_ReturnsNull() {
        String filePath = "src/test/resources/empty-file.txt"; // Beispiel: Leere Textdatei
        String loadedMessage = CustomEntitiesLoader.loadStandardMessage(new File(filePath).getAbsolutePath());
        assertNull(loadedMessage);
    }

    @Test
    void loadCustomEntities_FileIsEmpty_ReturnsNull() throws IOException {
        String filePath = "src/test/resources/empty-file.txt"; // Beispiel: Leere Textdatei
        assertNull(CustomEntitiesLoader.loadCustomEntities(new File(filePath).getAbsolutePath()));
    }

    @Test
    void uploadFile_IOException_ReturnsInternalServerError() {
        // Erstellen Sie ein Mock-MultipartFile-Objekt, das leer ist
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", new byte[0]);

        ResponseEntity<String> responseEntity = CustomEntitiesLoader.uploadFile(file);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
