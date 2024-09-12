package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.service.OrderItemService;
import hello.gccoffee.service.OrderMainService;
import hello.gccoffee.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Log4j2
public class OrderApiController implements OrderApiControllerDocs {

    private final OrderMainService orderMainService;
    private final OrderService orderService;            // 수정 필요!
    private final OrderItemService orderItemService;    // 수정 필요!

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> readOrder(@RequestParam("email") String email) {
        validateEmail(email);
        return ResponseEntity.ok(orderMainService.readOrder(email));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@Validated @RequestBody OrderDTO orderDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw OrderException.BAD_RESOURCE.get();
        }
        return ResponseEntity.ok(orderMainService.addOrders(orderDTO));
    }

    // 수정 필요!
    @PostMapping("/{orderId}")
    public ResponseEntity<Order> addOrderItems(@PathVariable Integer orderId,
                                               @Valid @RequestBody List<OrderItemDTO> items, // validated -> valid로 교체 : validated는 List안 orderItemDTO를 검증해주지 못함(실수가능성 있음)
                                               BindingResult bindingResult) {
        log.info("orderId = {}, items = {}", orderId, items.toString());
        if (bindingResult.hasErrors()) {
            throw OrderException.BAD_RESOURCE.get();
        }
        Order findOrder = orderService.findById(orderId);

        List<OrderItem> orderItemList = orderItemService.addItems(findOrder, items);

        return ResponseEntity.ok(findOrder);
        // 최종 리턴 형태
        // return ResponseEntity.ok(orderMainService.addOrderItems(orderId, items));
    }

//    //임시 조회
//    @GetMapping("/get")
//    public ResponseEntity<List<Order>> getOrdersByEmail(@RequestParam String email) {
//        log.info(email);
//        List<Order> allByEmail = orderService.findAllByEmail(email);
//        return ResponseEntity.ok(allByEmail);
//    }

////     주문, 주문자 전체 삭제
//    @DeleteMapping
//    public ResponseEntity<Map<String, String>> delete(@RequestParam("email") String email) {
//        checkEmail(email);
//        orderMainService.removeAll(email);
//        return ResponseEntity.ok(Map.of(
//                "result", "success",
//                "message", "Orders have been deleted successfully"
//        ));
//    }

    // 원하는 주문만 삭제
    @DeleteMapping("/{orderId}/{orderItemId}")
    public ResponseEntity<Map<String, String>> deleteOrder(@RequestParam("email") String email,
                                                           @PathVariable Integer orderId,
                                                           @PathVariable Integer orderItemId) {
        validateEmail(email);
        if (orderId == null || orderId.toString().isEmpty()) {
            throw OrderException.NOT_FOUND_ORDER.get();
        }
        if (orderItemId == null) {
            throw OrderException.NOT_FOUND_ORDER_ITEM.get();
        }
        orderMainService.removeOrder(email, orderId, orderItemId);
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "Order has been deleted successfully. Deleted ID: " + orderItemId
        ));
    }

    @PutMapping("/{orderId}/{orderItemId}")
    public ResponseEntity<OrderItemDTO> updateSingleOrderItem(@RequestParam("email") String email,
                                                              @PathVariable Integer orderId,
                                                              @PathVariable("orderItemId") Integer orderItemId,
                                                              @RequestBody @Validated OrderItemDTO orderItemDTO) {
        validateEmail(email);
        orderItemDTO.setOrderId(orderId);
        if (orderId == null || orderId.toString().isEmpty()) {
            throw OrderException.NOT_FOUND_ORDER.get();
        }
        if (orderItemId == null) {
            throw OrderException.NOT_FOUND_ORDER_ITEM.get();
        }
        orderItemDTO.setOrderItemId(orderItemId);
        return ResponseEntity.ok(orderMainService.updateOrderItemInOrder(orderItemDTO));
    }

    private static void validateEmail(String email) {
        // email 값이 비어있는 경우
        if (email == null || email.trim().isEmpty()) {
            throw OrderException.MISSING_EMAIL.get();
        }

        // email 형식이 잘못된 경우
        if (!email.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$")) {
            throw OrderException.INVALID_EMAIL.get();
        }
    }
}