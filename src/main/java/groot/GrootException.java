package groot;

/**
 * Represents a custom exception specific to the Groot application.
 * This exception is thrown when the application encounters errors related to
 * user input, task parsing, or storage operations.
 */
public class GrootException extends Exception {

    /**
     * Initializes a new GrootException with a specific error message.
     *
     * @param message The detailed error message describing the cause of the exception.
     */
    public GrootException(String message) {
        super(message);
    }
}
