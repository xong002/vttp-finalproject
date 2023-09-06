package project.vttpproject.exception;

public class DuplicatePropertyException extends Exception {
    public DuplicatePropertyException(){
        super("database access error");
    }

    public DuplicatePropertyException(String message){
        super(message);
    }

    public DuplicatePropertyException(String message, Throwable cause){
        super(message, cause);
    }
}
