package hello.gccoffee.controller.advice;

import hello.gccoffee.exception.OrderTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class OrderApiControllerAdvice {

    @ExceptionHandler(OrderTaskException.class)
    public ResponseEntity<Map<String, String>> handleOrderTaskException(OrderTaskException e) {
        return ResponseEntity.status(e.getCode()).body(Map.of(
                "result", "error",
                "message", e.getMessage()
        ));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "result", "error",
                "message", e.getMessage()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "result", "error",
                "message", e.getMessage()
        ));
    }
}