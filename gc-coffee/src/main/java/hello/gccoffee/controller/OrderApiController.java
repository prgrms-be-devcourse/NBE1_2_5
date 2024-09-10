package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.exception.OrderTaskException;
import hello.gccoffee.service.OrderItemService;
import hello.gccoffee.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderApiController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;


    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@Validated @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Order order = orderService.addOrders(orderDTO);
        return ResponseEntity.ok(order);

    }

    @PostMapping("/add/{orderId}")
    public ResponseEntity<Order> addOrderItems
            //validated -> valid로 교체 : validated는 List안 orderItemDTO를 검증해주지 못함(실수가능성 있음)
            (@PathVariable int orderId, @Valid @RequestBody List<OrderItemDTO> items, BindingResult bindingResult) {

        log.info("orderId = {}, items = {}",orderId,items.toString());
        if (bindingResult.hasErrors()) {
            throw OrderException.ORDER_NOT_FOUND.get();

        }
        Order findOrder = orderService.findById(orderId);

        List<OrderItem> orderItemList = orderItemService.addItems(findOrder, items);
                if (orderItemList == null) {
                    //필요한 부분인지 검증 필요
                    throw OrderException.ORDER_ITEM_NOT_FOUND.get();
                }

        return ResponseEntity.ok(findOrder);
    }
}
