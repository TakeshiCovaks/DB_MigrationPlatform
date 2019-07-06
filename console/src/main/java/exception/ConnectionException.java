package exception;

public class ConnectionException extends  RuntimeException {
    public ConnectionException(){
        super();
    }
    public ConnectionException(String message){
        super(message);
    }
    public ConnectionException(String message, Throwable cause){
        super(message, cause);
    }

}
