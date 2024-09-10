package hello.gccoffee.exception;

public enum ProductException {
    NOT_FOUND("PRODUCT_NOT_FOUND", 404),
    NOT_REGISTERED("Product NOT Registered", 400),
    FAIL_MODIFY("PRODUCT_FAILED_MODIFY", 400),

    NOT_REMOVED("Product NOT Removed", 400),
    NOT_FETCHED("Product NOT Fetched", 400),
    REGISTER_ERR("NO Authenticated user", 403);

    private ProductTaskException productTaskException;

    ProductException(String message, int code) {
        productTaskException = new ProductTaskException(message, code);
    }

    public ProductTaskException get() {
        return productTaskException;
    }
}
