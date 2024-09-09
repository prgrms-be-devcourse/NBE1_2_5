package hello.gccoffee.exception;

public enum ProductException {
    FAIL_REGISTER("FAIL_REGISTER", 400);

    private ProductTaskException productTaskException;
    ProductException(String message, int code) {
        this.productTaskException = new ProductTaskException(message, code);
    }
    public ProductTaskException get(){
        return productTaskException;
    }
}
