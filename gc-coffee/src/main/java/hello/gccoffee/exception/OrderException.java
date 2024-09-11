package hello.gccoffee.exception;

public enum OrderException {
    BAD_RESOURCE("bad resource", 400),
    ORDER_ITEM_NOT_FOUND("order item not found", 404),
    ORDER_NOT_REGISTERED("order item not registered", 400),
    ORDER_ITEM_NOT_REGISTERED("order item not registered", 400),
    WRONG_ORDER_ITEM_LIST("wrong error item list", 400),
    WRONG_ORDER_IN_ITEM_LIST("wrong order in item list", 400),
    ORDER_LIST_EXIST("order item list already exist",400),
    NOT_DELETE_ITEM("delete fail", 500),

    INVALID_ORDER_ITEM_QUANTITY("Invalid Order Item Quantity", 400),
    ORDER_ITEM_NOT_UPDATED("Order Item Not Updated", 400),

    MISSING_EMAIL("Email Missing", 400),
    MISSING_ORDER_ITEM_ID("Order Item Id Missing", 400),
    INVALID_EMAIL("Invalid Email", 400),

    NOT_FOUND_ORDER("ORDER Not Found", 404),
    NOT_FOUND_ORDER_ID("OrderId Not Found", 404);

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
