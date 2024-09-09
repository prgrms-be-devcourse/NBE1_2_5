package hello.gccoffee.exception;

public enum ProductException {
    FAIL_MODIFY("PRODUCT_FAILED_MODIFY", 400),
    NOT_FOUND("PRODUCT_NOT_FOUND", 404);

    private ProductTaskException productTaskException;
    ProductException(String message, int code) {
        this.productTaskException = new ProductTaskException(message, code);
    }
    public ProductTaskException get(){
        return productTaskException;
    }
}
