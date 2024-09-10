package hello.gccoffee.exception;

public enum OrderItemException {
    FAIL_DELETE("DELETE FAILED", 400);

    private final OrderItemTaskException orderItemTaskException;

    OrderItemException(String message, int code) {
        orderItemTaskException = new OrderItemTaskException(message, code);
    }

    public OrderItemTaskException get() {
        return orderItemTaskException;
    }
}
