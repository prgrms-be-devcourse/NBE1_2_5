package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.exception.AdminAuthenticationException;
import hello.gccoffee.service.OrderMainService;
import hello.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final ProductService productService;
    private final OrderMainService orderMainService;

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> registerProduct(@Validated @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.register(productDTO));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> readProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(productService.read(productId));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") Integer productId,
                                                    @Validated @RequestBody ProductDTO productDTO) {
        productDTO.setProductId(productId);
        return ResponseEntity.ok(productService.modify(productDTO));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Map<String, String>> removeProduct(@PathVariable("productId") Integer productId,
                                                             @RequestParam("adminPassword") String adminPassword) {
        validateAdminPassword(adminPassword);
        productService.remove(productId);
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "Product has been deleted successfully. Deleted ID: " + productId
        ));
    }

    // 관리자 주문 목록 조회
    @GetMapping("/orders/orderlist")
    public ResponseEntity<List<OrderItemDTO>> getOrderList(@RequestParam("adminPassword") String adminPassword) {
        validateAdminPassword(adminPassword);
        return ResponseEntity.ok(orderMainService.getList());
    }

    // 관리자 주문 내역 수정
    @PutMapping("/orders/{orderItemId}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@Validated @RequestBody OrderItemDTO orderItemDTO,
                                                        @PathVariable Integer orderItemId) {
        return ResponseEntity.ok(orderMainService.updateOrderItem(orderItemDTO, orderItemId));
    }

    // 이메일에 해당하는 단일 주문 삭제
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Map<String, String>> deleteOneOrder(@PathVariable Integer orderId, @RequestParam String email) {
        orderMainService.removeOrder(orderId, email);
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "Order has been deleted successfully. Deleted ID: " + orderId
        ));
    }

    // 이메일에 해당하는 모든 주문 삭제
    @DeleteMapping("/orders")
    public ResponseEntity<Map<String, String>> deleteAllOrder(@RequestParam String email) {
        List<Integer> idList = orderMainService.removeAllOrder(email);
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "Order has been deleted successfully. Deleted ID: " + idList.toString()
        ));
    }

    // 수정 필요!
//    // 단일 아이템 삭제
//    @PostMapping("/orders/{orderId}")
//    public ResponseEntity<Map<String, String>> deleteOneItemInOrder
//    (@PathVariable int orderId, @RequestParam String email, @Validated @RequestBody OrderItemDTO orderItemDTO) {
//        //확인절차
//        List<Integer> orderIdsByEmail = orderService.findOrderIdsByEmail(email);
//        Map<String, String> map;
//        if (orderIdsByEmail.contains(orderId)) {
//            //삭제로직
//            try {
//                Integer productId = orderItemService.deleteOneItem(orderItemDTO, orderId);
//                orderService.deleteOneItemInOrder(orderItemDTO, productId, orderId);
//            } catch (OrderTaskException e) {
//                //실패반환
//                map = Map.of("message", "delete fail");
//                return ResponseEntity.ok(map);
//            }
//
//            //성공반환
//            map = Map.of("message", "delete complete");
//            return ResponseEntity.ok(map);
//        }
//        //주문번호가 해당 이메일이 아니면 실패 반환
//        map = Map.of("message", "order not exist");
//        return ResponseEntity.ok(map);
//    }

    private void validateAdminPassword(String adminPassword) {
        if (adminPassword == null || adminPassword.isEmpty()) {
            throw new AdminAuthenticationException("Admin password is required");
        }
        if (!"1111".equals(adminPassword)) {
            throw new AdminAuthenticationException("Unauthorized");
        }
    }
}
