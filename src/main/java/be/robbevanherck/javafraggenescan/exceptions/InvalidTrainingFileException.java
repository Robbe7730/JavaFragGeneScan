package be.robbevanherck.javafraggenescan.exceptions;

/**
 * Thrown when there's something wrong with the files in the "train" folder.
 * RuntimeException because this is a user error, not a (recoverable) programming error.
 */
public class InvalidTrainingFileException extends RuntimeException {
    /**
     * Create an exception with a message and a nested exception
     * @param message The message
     * @param nested The nested exception
     */
    public InvalidTrainingFileException(String message, Throwable nested) {
        super(message, nested);
    }
}
