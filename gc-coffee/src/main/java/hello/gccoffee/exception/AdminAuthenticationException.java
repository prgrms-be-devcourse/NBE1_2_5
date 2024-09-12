package hello.gccoffee.exception;

public class AdminAuthenticationException extends RuntimeException {
    public AdminAuthenticationException(String message) {
        super(message);
    }
}
