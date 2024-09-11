package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.service.OrderMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Log4j2
public class OrderApiController {
    private final OrderMainService orderMainService;

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> read(@RequestParam("email") String email) {
        log.info("===== read() =====");

        // email 값이 비어있는 경우
        if (email == null || email.trim().isEmpty()) {
            throw OrderException.MISSING_EMAIL.get();
        }

        // email 형식이 잘못된 경우
        if (!email.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$")) {
            throw OrderException.INVALID_EMAIL.get();
        }

        log.info("APIController ===> orderItemsDTOS 호출");
        List<OrderItemDTO> orderItemDTOS = orderMainService.readOrder(email);
        log.info("APIController ===> orderMainService에서 orderItemDTOs 반환 : " + orderItemDTOS);
        return ResponseEntity.ok(orderMainService.readOrder(email));
    }

    @GetMapping("/orderlist")   //관리자 주문 목록 조회
    public ResponseEntity<?> getList(@RequestParam("adminPassword") String adminPassword) {

        log.info("===== getList() =====");

        //비밀번호 없을 때
        if (adminPassword == null || adminPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin password is required");
        }

        //비밀번호 틀렸을 때
        if (!"1111".equals(adminPassword)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect admin password.");
        }
        return ResponseEntity.ok(orderMainService.getList());
    }

}
