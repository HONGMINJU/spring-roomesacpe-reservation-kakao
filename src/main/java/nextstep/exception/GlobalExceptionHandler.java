package nextstep.exception;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity handleCannotDeleteException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(JdbcSQLException.class)
    public ResponseEntity handleJdbcSQLException(Exception e) {
        return ResponseEntity.badRequest().body("sql error");
    }
}
