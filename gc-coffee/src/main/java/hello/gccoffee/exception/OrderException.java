package hello.gccoffee.exception;

public enum OrderException {
    ORDER_NOT_FOUND("order not found", 404),
    ORDER_ITEM_NOT_FOUND("order item not found", 404),
    ORDER_NOT_REGISTERED("order item not registered", 400),
    ORDER_ITEM_NOT_REGISTERED("order item not registered", 400);

    private final OrderTaskException orderTaskException;

    OrderException(String messages, int code) {
        orderTaskException = new OrderTaskException(messages, code);
    }
    public OrderTaskException getOrderTaskException() {
        return orderTaskException;
    }

    public OrderTaskException get() {
        return orderTaskException;
    }
}
