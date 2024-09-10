package hello.gccoffee.controller;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductDTO> read(@PathVariable("pno") long pno) {
        log.info("Product id " + pno);
        return ResponseEntity.ok(productService.read(pno));
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(@Validated @RequestBody ProductDTO productDTO) {
        ProductDTO modifiedProductDTO = productService.modify(productDTO);
        return ResponseEntity.ok(modifiedProductDTO);
    }
}
