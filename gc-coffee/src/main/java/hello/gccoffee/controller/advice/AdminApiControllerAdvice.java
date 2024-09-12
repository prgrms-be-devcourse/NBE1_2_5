package hello.gccoffee.controller.advice;

import hello.gccoffee.exception.AdminAuthenticationException;
import hello.gccoffee.exception.ProductTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

@RestControllerAdvice
public class AdminApiControllerAdvice {

    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<Map<String, String>> handleProductTaskException(ProductTaskException e) {
        return ResponseEntity.status(e.getCode()).body(Map.of(
                "result", "error",
                "message", e.getMessage()
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "result", "error",
                "message", "[JSON] 형식을 확인해주세요."
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "result", "error",
                "message", e.getFieldError().getDefaultMessage()
        ));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "result", "error",
                "message", "URL을 잘못 입력하였습니다."
        ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "result", "error",
                "message", "입력값의 형식을 확인해주세요."
        ));
    }

    @ExceptionHandler(AdminAuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAdminAuthenticationException(AdminAuthenticationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                "result", "error",
                "message", e.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "result", "error",
                "message", "An unexpected error occurred"
        ));
    }
}
