package project.vttpproject.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.json.Json;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        return ResponseEntity
                .status(500)
                .body(Json.createObjectBuilder()
                        .add("error", ex.getMessage())
                        .build()
                        .toString());
    }

    // @ExceptionHandler(DatabaseException.class)
    // protected ResponseEntity<String> handleDataBaseException(DatabaseException ex) {
    //     return ResponseEntity
    //             .status(500)
    //             .body(Json.createObjectBuilder()
    //                     .add("error", ex.getMessage())
    //                     .build()
    //                     .toString());
    // }
}
