package hello.gccoffee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItemTaskException extends RuntimeException {
    private String message;
    private int code;
}
