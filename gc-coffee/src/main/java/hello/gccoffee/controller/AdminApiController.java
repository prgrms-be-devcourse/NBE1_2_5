package hello.gccoffee.controller;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coffee")
@RequiredArgsConstructor
public class AdminApiController {

    private final ProductService productService;

    @PutMapping
    public ResponseEntity<ProductDTO> update(@Validated @RequestBody ProductDTO productDTO) {
        ProductDTO modifiedProductDTO = productService.modify(productDTO);
        return ResponseEntity.ok(modifiedProductDTO);
    }
}
