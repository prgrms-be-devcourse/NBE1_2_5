package hello.gccoffee.controller.advice;

import hello.gccoffee.exception.ProductTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdminApiControllerAdvice {

    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<Map<String, String>> handleProductTaskException(ProductTaskException e) {
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(e.getCode()).body(map);
    }
}
