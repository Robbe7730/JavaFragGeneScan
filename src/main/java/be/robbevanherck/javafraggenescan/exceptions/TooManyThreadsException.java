package be.robbevanherck.javafraggenescan.exceptions;

/**
 * Thrown when there are more threads being started than -p/--max-threads asked
 */
public class TooManyThreadsException extends RuntimeException {

    /**
     * Create a new exception with a message
     * @param message The message to display
     */
    public TooManyThreadsException(String message) {
        super(message);
    }
}
