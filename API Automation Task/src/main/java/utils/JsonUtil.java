package utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for JSON file operations using Jackson ObjectMapper.
 * Provides static methods for reading JSON files and converting them to Java Map objects.
 * This class simplifies JSON parsing operations commonly used in test data loading.
 */
public class JsonUtil {

    /** Static ObjectMapper instance for JSON serialization/deserialization operations */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads a JSON file and converts it to a Map of key-value pairs.
     * Uses Jackson ObjectMapper to parse the JSON file into a generic Map structure.
     * Handles IOException by printing stack trace and returning null.
     *
     * @param filePath The absolute or relative path to the JSON file to be read
     * @return Map containing the parsed JSON data, or null if an IOException occurs
     */
    public static Map<String, Object> readJsonFile(String filePath) {
        try {
            return objectMapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
