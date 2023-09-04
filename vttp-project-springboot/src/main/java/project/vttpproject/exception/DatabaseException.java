package project.vttpproject.exception;

public class DatabaseException extends Exception{
    
    public DatabaseException(){
        super("database access error");
    }

    public DatabaseException(String message){
        super(message);
    }

    public DatabaseException(String message, Throwable cause){
        super(message, cause);
    }

}
