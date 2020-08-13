package be.robbevanherck.javafraggenescan.exceptions;

/**
 * Thrown when something goes wrong when running the HMM/ViterbiAlgorithm
 * RuntimeException because this is most likely a user error, not a (recoverable) programming error.
 */
public class RunningException extends RuntimeException {
    /**
     * Create an exception with a message and a nested exception
     * @param message The message
     * @param nested The nested exception
     */
    public RunningException(String message, Throwable nested) {
        super(message, nested);
    }
}
