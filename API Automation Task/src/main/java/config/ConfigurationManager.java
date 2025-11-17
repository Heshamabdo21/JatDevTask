package config;

import java.io.IOException;
import java.util.Properties;

/**
 * Configuration management utility for loading and accessing application properties.
 * Provides a centralized way to read configuration values from the config.properties file
 * located in the classpath resources. Properties are loaded once at class initialization
 * and cached for efficient access throughout the application lifecycle.
 */
public class ConfigurationManager {

    /** Static Properties object to hold loaded configuration values */
    private static Properties properties;

    static {
        properties = new Properties();
        try (var is = ConfigurationManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a configuration property value by its key.
     * Returns the string value associated with the specified key from the loaded properties.
     * Returns null if the key is not found.
     *
     * @param key The property key to look up in the configuration
     * @return The string value of the property, or null if not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
