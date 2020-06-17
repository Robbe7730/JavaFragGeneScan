package be.robbevanherck.javafraggenescan;

/**
 * Thrown when there's something wrong with the output
 * RuntimeException because this is a user error, not a (recoverable) programming error.
 */
public class OutputException extends RuntimeException {
    /**
     * Create an exception with a message and a nested exception
     * @param message The message
     * @param nested The nested exception
     */
    public OutputException(String message, Throwable nested) {
        super(message, nested);
    }
}
