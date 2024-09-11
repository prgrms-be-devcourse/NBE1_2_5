package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.exception.OrderException;
import hello.gccoffee.service.OrderMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Log4j2
public class OrderApiController {
    private final OrderMainService orderMainService;

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> read(@RequestParam("email") String email) {
        log.info("===== read() =====");

        checkEmail(email);

        log.info("APIController ===> orderItemsDTOS 호출");
        List<OrderItemDTO> orderItemDTOS = orderMainService.readOrder(email);
        log.info("APIController ===> orderMainService에서 orderItemDTOs 반환 : " + orderItemDTOS);
        return ResponseEntity.ok(orderMainService.readOrder(email));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(@RequestParam("email") String email) {
        checkEmail(email);
        orderMainService.removeOrder(email);
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "Product has been deleted successfully"
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

