package be.robbevanherck.javafraggenescan.exceptions;

/**
 * Exception that occurs during config reading
 */
public class ConfigReadException extends RuntimeException {
    /**
     * Exception with just a message
     * @param message The message describing the error
     */
    public ConfigReadException(String message) {
        super(message);
    }

    /**
     * Exception with a message and a nested Exception
     * @param message The message describing the error
     * @param throwable The nested Exception
     */
    public ConfigReadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
