package hello.gccoffee.controller;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.exception.AdminAuthenticationException;
import hello.gccoffee.exception.ProductTaskException;
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

    @DeleteMapping("/{pno}")
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
}