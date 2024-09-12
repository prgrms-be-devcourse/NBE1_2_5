package hello.gccoffee.exception;

public enum ProductException {
    // 400 Bad Request
    PRODUCT_NOT_REGISTERED("Product Not Registered", 400),
    PRODUCT_NOT_UPDATED("Product Not Updated", 400),
    PRODUCT_NOT_REMOVED("Product Not Removed", 400),
    PRODUCT_NOT_FETCHED("Product Not Fetched", 400),

    // 403 Forbidden
    NOT_AUTHENTICATED_USER("Not Authenticated User", 403),

    // 404 Not Found
    PRODUCT_NOT_FOUND("Product Not Found", 404);

    private ProductTaskException productTaskException;

    ProductException(String message, int code) {
        productTaskException = new ProductTaskException(message, code);
    }

    public ProductTaskException get() {
        return productTaskException;
    }
}
