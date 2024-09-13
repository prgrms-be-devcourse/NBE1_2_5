package hello.gccoffee.exception;

public enum OrderException {
    // 400 Bad Request
    INVALID_RESOURCE("Invalid Resource", 400),
    ORDER_NOT_REGISTERED("Order Not Registered", 400),
    ORDER_ITEM_NOT_REGISTERED("Order Item Not Registered", 400),
    INVALID_ORDER_DETAILS("Invalid Order Details", 400),
    ORDER_LIST_ALREADY_EXISTS("Order Item List Already Exists", 400),
    INVALID_ORDER_ITEM_QUANTITY("Invalid Order Item Quantity", 400),
    ORDER_ITEM_NOT_UPDATED("Order Item Not Updated", 400),
    MISSING_EMAIL("Email Missing", 400),
    INVALID_EMAIL("Invalid Email", 400),

    // 404 Not Found
    ORDER_NOT_FOUND("Order Not Found", 404),
    ORDER_ITEM_NOT_FOUND("Order Item Not Found", 404),
    ORDER_ID_NOT_FOUND("Order ID Not Found", 404),

    // 500 Internal Server Error
    ORDER_NOT_REMOVED("Order Not Removed", 500),
    ORDER_ITEM_NOT_REMOVED("Item Deletion Failed", 500);

    private final OrderTaskException orderTaskException;

    OrderException(String message, int code) {
        orderTaskException = new OrderTaskException(message, code);
    }

    public OrderTaskException get() {
        return orderTaskException;
    }
}
