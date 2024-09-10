package hello.gccoffee.controller;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class AdminApiController {
    private final ProductService productService;

    @GetMapping("/{pno}")
    public ResponseEntity<ProductDTO> read(@PathVariable("pno") int pno) {
        log.info("Product id " + pno);
        return ResponseEntity.ok(productService.read(pno));
    }


}
