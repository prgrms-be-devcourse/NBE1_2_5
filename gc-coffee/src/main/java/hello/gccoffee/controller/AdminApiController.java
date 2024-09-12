package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.exception.AdminAuthenticationException;
import hello.gccoffee.exception.OrderTaskException;
import hello.gccoffee.service.OrderItemService;
import hello.gccoffee.service.OrderMainService;
import hello.gccoffee.service.OrderService;
import hello.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminApiController implements AdminApiControllerDocs {
    private final ProductService productService;

    private final OrderMainService orderMainService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> registerProduct(@Validated @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.register(productDTO));
    }

    @GetMapping("/products/{pno}")
    public ResponseEntity<ProductDTO> read(@PathVariable("pno") int pno) {
        log.info("Product id " + pno);
        return ResponseEntity.ok(productService.read(pno));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> update(@PathVariable("productId") int productId,
                                             @Validated @RequestBody ProductDTO productDTO) {
        productDTO.setProductId(productId);
        ProductDTO modifiedProductDTO = productService.modify(productDTO);
        return ResponseEntity.ok(modifiedProductDTO);
    }

    @DeleteMapping("/products/{pno}")
    public ResponseEntity<Map<String, String>> remove(@RequestParam("adminPassword") String adminPassword,
                                                      @PathVariable("pno") Integer pno) {
        log.info("--- remove() ---");
        log.info("--- pno : " + pno + " ---");

        validateAdminPassword(adminPassword);
        productService.remove(pno);

        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "Product has been deleted successfully",
                "deletedId", pno.toString()
        ));
    }

    private void validateAdminPassword(String adminPassword) {
        if (adminPassword == null || adminPassword.isEmpty()) {
            throw new AdminAuthenticationException("Admin password is required");
        }
        if (!"1111".equals(adminPassword)) {
            throw new AdminAuthenticationException("Unauthorized");
        }
    }

    @PutMapping("/orders/{orderItemId}") //관리자 상품 주문 수정
    public ResponseEntity<OrderItemDTO> update(@Validated
                                               @RequestBody OrderItemDTO orderItemDTO,
                                               @PathVariable int orderItemId) {

        return ResponseEntity.ok(orderMainService.updateOrderItem(orderItemDTO, orderItemId));
    }

    @GetMapping("/orders/orderlist")   //관리자 주문 목록 조회
    public ResponseEntity<?> getList(@RequestParam("adminPassword") String adminPassword) {

        log.info("===== getList() =====");


        validateAdminPassword(adminPassword);


//        //비밀번호 없을 때
//        if (adminPassword == null || adminPassword.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin password is required");
//        }
//
//        //비밀번호 틀렸을 때
//        if (!"1111".equals(adminPassword)){
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body("Incorrect admin password.");
//        }
        return ResponseEntity.ok(orderMainService.getList());
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
}
