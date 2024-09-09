package hello.gccoffee.exception;

import lombok.Getter;

@Getter
public class ProductTaskException extends RuntimeException{
    private String message;
    private int code;

    public ProductTaskException(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
