package exception;

/**
 * Errors related to the serialization of the tree structure.
 */
public class SerialzableException extends Exception {

    public SerialzableException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerialzableException(String message) {
        super(message);
    }
}
