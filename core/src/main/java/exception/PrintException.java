package exception;

public class PrintException extends RuntimeException {

    public PrintException() {
        super();
    }

    public PrintException(String message) {
        super(message);
    }

    public PrintException(String message, Throwable cause) {
        super(message, cause);
    }
}
