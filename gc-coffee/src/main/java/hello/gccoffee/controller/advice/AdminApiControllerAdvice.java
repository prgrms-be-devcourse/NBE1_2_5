package hello.gccoffee.controller.advice;

import hello.gccoffee.exception.ProductTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleProductTaskException(HttpMessageNotReadableException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "[JSON]" + " 형식을 확인해주세요");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleProductTaskException(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", e.getMessage().substring(e.getMessage().indexOf("[*")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductTaskException(NoResourceFoundException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "URL을 잘못 입력하였습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("message", e.getFieldError().getDefaultMessage());
        errMap.put("code", 400);
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("message", "존재하지 않는 상품 카테고리입니다");
        errMap.put("code", 400);
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }
}
