package project.vttpproject.exception;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.json.Json;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<String> handleJsonProcessingException(JsonProcessingException ex) {
        return ResponseEntity
                .status(400)
                .body(Json.createObjectBuilder()
                        .add("error", ex.getMessage())
                        .build()
                        .toString());
    }

    @ExceptionHandler(UpdateException.class)
    protected ResponseEntity<String> handleDataBaseException(UpdateException ex) {
        return ResponseEntity
                .status(400)
                .body(Json.createObjectBuilder()
                        .add("error", ex.getMessage())
                        .build()
                        .toString());
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<String> handleSQLException(SQLException ex) {
        return ResponseEntity
                .status(500)
                .body(Json.createObjectBuilder()
                        .add("error", ex.getMessage())
                        .build()
                        .toString());
    }

}
