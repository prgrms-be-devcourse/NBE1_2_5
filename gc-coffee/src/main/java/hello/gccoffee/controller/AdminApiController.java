package hello.gccoffee.controller;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.exception.ProductTaskException;
import hello.gccoffee.service.OrderMainService;
import hello.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class AdminApiController {
    private final ProductService productService;
    private final OrderMainService orderMainService;


    @PostMapping
    public ResponseEntity<ProductDTO> registerProduct(@Validated @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.register(productDTO));
    }

    @GetMapping("/{pno}")
    public ResponseEntity<ProductDTO> read(@PathVariable("pno") int pno) {
        log.info("Product id " + pno);
        return ResponseEntity.ok(productService.read(pno));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> update(@PathVariable("productId") int productId,
                                             @Validated @RequestBody ProductDTO productDTO) {
        productDTO.setProductId(productId);
        ProductDTO modifiedProductDTO = productService.modify(productDTO);
        return ResponseEntity.ok(modifiedProductDTO);
    }

    @DeleteMapping("/{pno}")  // 관리자 삭제 페이지
    public ResponseEntity<Map<String, String>> remove(@RequestParam("adminPassword") String adminPassword,
                                                      @PathVariable("pno") Integer pno) {
        log.info("--- remove() ---");
        log.info("--- pno : " + pno + " ---");

        // 비밀번호가 없을 때 예외 처리
        if (adminPassword == null || adminPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "result", "error",
                    "message", "Admin password is required"
            ));
        }

        // 관리자 인증??
        if (!"1111".equals(adminPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "result", "error",
                    "message", "Unauthorized"
            ));
        }

        //예외처리
        try {
            productService.remove(pno);
            return ResponseEntity.ok(Map.of(
                    "result", "success",
                    "message", "Product has been deleted successfully",
                    "deletedId", pno.toString()
            ));
        } catch (ProductTaskException e) {
            // 서비스 메서드에서 발생한 삭제실패 예외
            log.error("Error occurred while deleting product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "result", "error",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            // 다른 예외 처리
            log.error("Unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "result", "error",
                    "message", "An unexpected error occurred"
            ));
        }
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
