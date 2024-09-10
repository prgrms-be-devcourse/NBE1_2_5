package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.service.OrderItemService;
import hello.gccoffee.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if (bindingResult.hasErrors()) {
            //잘못된 바인딩 결과는 404로 확인 검증 완료(quantity -1대입, null일경우 등등)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        Order findOrder = orderService.findById(orderId);
        List<OrderItem> orderItemList = orderItemService.addItems(findOrder, items);
                if (orderItemList == null) {
                    //필요한 부분인지 검증 필요
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

        return ResponseEntity.ok(findOrder);
    }
}
