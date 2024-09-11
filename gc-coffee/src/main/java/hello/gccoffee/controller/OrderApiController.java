package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.exception.OrderTaskException;
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
public class OrderApiController {
    private final OrderMainService orderMainService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> read(@RequestParam("email") String email) {
        log.info("===== read() =====");

        checkEmail(email);

        log.info("APIController ===> orderItemsDTOS 호출");
        List<OrderItemDTO> orderItemDTOS = orderMainService.readOrder(email);
        log.info("APIController ===> orderMainService에서 orderItemDTOs 반환 : " + orderItemDTOS);
        return ResponseEntity.ok(orderMainService.readOrder(email));
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@Validated @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw OrderException.BAD_RESOURCE.get();
        }

        Order order = orderService.addOrders(orderDTO);

        return ResponseEntity.ok(order);

    }

    @PostMapping("/{orderId}")
    public ResponseEntity<Order> addOrderItems
            //validated -> valid로 교체 : validated는 List안 orderItemDTO를 검증해주지 못함(실수가능성 있음)
            (@PathVariable int orderId, @Valid @RequestBody List<OrderItemDTO> items, BindingResult bindingResult) {

        log.info("orderId = {}, items = {}",orderId,items.toString());
        if (bindingResult.hasErrors()) {
            throw OrderException.BAD_RESOURCE.get();
        }
        Order findOrder = orderService.findById(orderId);

        List<OrderItem> orderItemList = orderItemService.addItems(findOrder, items);

        return ResponseEntity.ok(findOrder);
    }

    //임시 조회
    @GetMapping("/get")
    public ResponseEntity<List<Order>> getOrdersByEmail(@RequestParam String email) {
        log.info(email);
        List<Order> allByEmail = orderService.findAllByEmail(email);
        return ResponseEntity.ok(allByEmail);
    }

    //이메일에 해당하는 단일 주문 삭제
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> deleteOneOrder(@PathVariable int orderId, @RequestParam String email) {
        //확인절차
        List<Integer> orderIdsByEmail = orderService.findOrderIdsByEmail(email);
        Map<String, String> map;
        if (orderIdsByEmail.contains(orderId)) {
            //삭제로직
            try {
                orderItemService.deleteAllByOrderId(orderId);
                orderService.deleteOneOrderOfOne(orderId);
            } catch (OrderTaskException e) {
                //실패반환
                map = Map.of("message", "delete fail");
                return ResponseEntity.ok(map);
            }

            //성공반환
            map = Map.of("message", "delete complete");
            return ResponseEntity.ok(map);
        }
        //주문번호가 해당 이메일이 아니면 실패 반환
        map = Map.of("message", "order not exist");
        return ResponseEntity.ok(map);

    }
    //이메일에 해당하는 모든 주문 삭제
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAllOrder(@RequestParam String email) {

        Map<String, String> map;

        try {
            List<Integer> orderIdsByEmail = orderService.findOrderIdsByEmail(email);
            for (Integer orderId : orderIdsByEmail) {
                orderItemService.deleteAllByOrderId(orderId);
            }
            orderService.deleteAllOrderOfOne(email);
            map = Map.of("message", "delete complete");
            return ResponseEntity.ok(map);
        } catch (OrderTaskException e) {
            map = Map.of("message", "delete fail");
            return ResponseEntity.ok(map);
        }

    }
    //단일 아이템 삭제
    @PostMapping("/delete/{orderId}")
    public ResponseEntity<Map<String, String>> deleteOneItemInOrder
    (@PathVariable int orderId, @RequestParam String email, @Validated @RequestBody OrderItemDTO orderItemDTO) {
        //확인절차
        List<Integer> orderIdsByEmail = orderService.findOrderIdsByEmail(email);
        Map<String, String> map;
        if (orderIdsByEmail.contains(orderId)) {
            //삭제로직
            try {
                Integer productId = orderItemService.deleteOneItem(orderItemDTO, orderId);
                orderService.deleteOneItemInOrder(orderItemDTO, productId, orderId);
            } catch (OrderTaskException e) {
                //실패반환
                map = Map.of("message", "delete fail");
                return ResponseEntity.ok(map);
            }

            //성공반환
            map = Map.of("message", "delete complete");
            return ResponseEntity.ok(map);
        }
        //주문번호가 해당 이메일이 아니면 실패 반환
        map = Map.of("message", "order not exist");
        return ResponseEntity.ok(map);

    }


    // 주문, 주문자 전체 삭제
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
                                                           @PathVariable int orderId,
                                                           @PathVariable int orderItemId) {
        checkEmail(email);
        orderMainService.removeOrder(email, orderId, orderItemId);
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "Order has been deleted successfully"
        ));
    }



    private static void checkEmail(String email) {
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

