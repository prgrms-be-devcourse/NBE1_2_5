package hello.gccoffee.swagger;

import hello.gccoffee.exception.OrderException;
import hello.gccoffee.exception.ProductException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface orderApiErrorCode {
    OrderException[] orderValue();
    ProductException[] productValue();
}
