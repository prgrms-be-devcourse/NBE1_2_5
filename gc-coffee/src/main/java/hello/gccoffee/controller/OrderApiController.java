package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.service.OrderItemService;
import hello.gccoffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderApiController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

//    @GetMapping("/add")
//    public ResponseEntity<Order> orderForm(@Validated @RequestBody Order order, BindingResult bindingResult) {
//
//    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@Validated @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        Order order = orderService.addOrders(orderDTO);
        int orderId = order.getOrderId();

        return ResponseEntity.ok(order);

    }

    @PostMapping("/add/{orderId}")
    public ResponseEntity<Order> addOrderItems
            (@PathVariable int orderId, @Validated @RequestBody List<OrderItemDTO> items,BindingResult bindingResult) {
        Order findOrder = orderService.findById(orderId);
        List<OrderItem> orderItemList = orderItemService.addItems(findOrder, items);
        for (OrderItem orderItem : orderItemList) {
            findOrder.addOrderItems(orderItem);
        }

        return ResponseEntity.ok(findOrder);
    }
}
