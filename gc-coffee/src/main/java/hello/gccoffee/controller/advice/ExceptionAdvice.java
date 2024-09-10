package hello.gccoffee.controller.advice;

import hello.gccoffee.exception.OrderTaskException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Log4j2
public class ExceptionAdvice {

    @ExceptionHandler(OrderTaskException.class)
    public ResponseEntity<Map<String, String>> handleException(OrderTaskException e) {
        log.info("OrderTaskException : " + e.getMessage());

        Map<String, String> errMap = Map.of("error", e.getMessage());
        return ResponseEntity.status(e.getCode()).body(errMap);
    }

}
