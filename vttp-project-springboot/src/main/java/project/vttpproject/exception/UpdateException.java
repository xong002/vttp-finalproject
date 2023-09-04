package project.vttpproject.exception;

public class UpdateException extends Exception{
    
    public UpdateException(){
        super("database access error");
    }

    public UpdateException(String message){
        super(message);
    }

    public UpdateException(String message, Throwable cause){
        super(message, cause);
    }

}
