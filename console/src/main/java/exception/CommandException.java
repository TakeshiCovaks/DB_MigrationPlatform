package exception;

/**
 * Exceptions occurring in the absence of valid command
 */
public class CommandException extends RuntimeException {
    public CommandException(String message){
        super(message);
    }

    public CommandException(String message, Throwable cause){
        super(message, cause);
    }
}
