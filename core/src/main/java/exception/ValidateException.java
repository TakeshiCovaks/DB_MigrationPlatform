package exception;

/**
 * Exception associated with validating input data for commands.
 */
public class ValidateException extends Exception {
    public ValidateException(){
        super();
    }
    public ValidateException(String message){
        super(message);
    }
}
