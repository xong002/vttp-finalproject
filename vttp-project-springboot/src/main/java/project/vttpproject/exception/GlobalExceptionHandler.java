package project.vttpproject.exception;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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

        @ExceptionHandler(NotFoundException.class)
        protected ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
                return ResponseEntity
                                .status(404)
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

        @ExceptionHandler(DuplicatePropertyException.class)
        protected ResponseEntity<String> handleDuplicatePropertException(DuplicatePropertyException ex) {
                return ResponseEntity
                                .status(200)
                                .body(Json.createObjectBuilder()
                                                .add("message", ex.getMessage())
                                                .build()
                                                .toString());
        }

        @ExceptionHandler(DuplicateKeyException.class)
        protected ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException ex) {
                System.out.println(ex);
                return ResponseEntity
                                .status(409)
                                .body(Json.createObjectBuilder()
                                                .add("error", ex.getMessage())
                                                .build()
                                                .toString());
        }

        @ExceptionHandler(DataAccessException.class)
        protected ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
                return ResponseEntity
                                .status(200)
                                .body(Json.createObjectBuilder()
                                                .add("error", ex.getMessage())
                                                .build()
                                                .toString());
        }

        @ExceptionHandler(IOException.class)
        protected ResponseEntity<String> handleIOException(IOException ex) {
                return ResponseEntity
                                .status(200)
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
