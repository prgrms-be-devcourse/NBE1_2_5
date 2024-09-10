package hello.gccoffee.exception;

public enum OrderException {
    MISSING_EMAIL("Email Missing", 400),
    INVALID_EMAIL("Invalid Email", 400),

    NOT_FOUND_ORDER("ORDER Not Found", 404),
    NOT_FOUND_ORDERID("OrderId Not Found", 404);

    private final OrderTaskException orderTaskException;

    OrderException(String message, int code) {
        orderTaskException = new OrderTaskException(message, code);
    }

    public OrderTaskException getOrderTaskException() {
        return orderTaskException;
    }

    public OrderTaskException get() {
        return orderTaskException;
    }
}
