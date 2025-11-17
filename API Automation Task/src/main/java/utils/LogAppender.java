package utils;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * Custom Log4j2 appender for capturing log messages in memory.
 * This appender extends AbstractAppender to intercept log events and store them
 * in a static StringBuilder buffer for later retrieval and reporting purposes,
 * particularly useful for attaching logs to test reports like Allure.
 */
@Plugin(name = "LogAppender", category = "Core", elementType = "appender", printObject = true)
public class LogAppender extends AbstractAppender {

    /** Static buffer to store captured log messages across all instances */
    private static final StringBuilder logBuffer = new StringBuilder();

    /**
     * Protected constructor for creating a new LogAppender instance.
     * Initializes the appender with the specified name and layout pattern.
     *
     * @param name The name of the appender instance
     * @param layout The pattern layout for formatting log messages
     */
    protected LogAppender(String name, PatternLayout layout) {
        super(name, null, layout, true, null);
    }

    /**
     * Factory method for creating LogAppender instances through Log4j2 plugin system.
     * Creates a new appender with default pattern layout configuration.
     *
     * @param name The name attribute for the appender from configuration
     * @return A new LogAppender instance configured with default layout
     */
    @PluginFactory
    public static LogAppender createAppender(@PluginAttribute("name") String name) {
        PatternLayout layout = PatternLayout.createDefaultLayout();
        return new LogAppender(name, layout);
    }

    /**
     * Appends a log event to the internal buffer.
     * Converts the log event to a formatted string using the configured layout
     * and appends it to the static log buffer with a line separator.
     *
     * @param event The LogEvent containing the log message and metadata
     */
    @Override
    public void append(LogEvent event) {
        logBuffer.append(new String(getLayout().toByteArray(event))).append(System.lineSeparator());
    }

    /**
     * Retrieves all captured log messages from the buffer.
     * Returns the complete contents of the log buffer as a string.
     *
     * @return String containing all accumulated log messages
     */
    public static String getLogs() {
        return logBuffer.toString();
    }

    /**
     * Clears the log buffer by resetting its length to zero.
     * Removes all previously captured log messages from memory.
     */
    public static void clearLogs() {
        logBuffer.setLength(0);
    }
}
