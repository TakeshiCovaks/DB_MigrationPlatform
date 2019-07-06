package exception;

/**
 * Exception  due to invalid input.
 */
public class InputException extends RuntimeException {

    public InputException(String message) {
        super(message);
    }

}