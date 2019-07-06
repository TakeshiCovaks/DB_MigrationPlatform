package exception;


/**
 * Exception  related to deserialization of data into a tree structure.
 */
public class ParserException extends Exception {

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message) {
        super(message);
    }
}
