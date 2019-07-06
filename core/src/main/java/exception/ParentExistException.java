package exception;

/**
 * Exception related to filling the tree structure
 */
public class ParentExistException extends RuntimeException {
    public ParentExistException(String message){
        super(message);
    }
}
