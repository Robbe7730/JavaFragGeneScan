package be.robbevanherck.javafraggenescan;

/**
 * Thrown when there's something wrong with the input on standard input
 * RuntimeException because this is a user error, not a (recoverable) programming error.
 */
public class InvalidInputException extends RuntimeException {
    /**
     * Create an exception with a message and a nested exception
     * @param message The message
     * @param nested The nested exception
     */
    public InvalidInputException(String message, Throwable nested) {
        super(message, nested);
    }
}
