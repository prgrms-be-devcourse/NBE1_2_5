package hello.gccoffee.controller;

import hello.gccoffee.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "관리자 기능 API", description = "관리자의 상품 목록 조회 및 주문 요청 처리를 담당합니다.")
public interface AdminApiControllerDocs {

    @Operation(summary = "상품 등록", description = "관리자가 판매할 상품 정보를 입력하여 상품을 등록합니다.")
    ResponseEntity<ProductDTO> registerProduct(@Validated @RequestBody ProductDTO productDTO);
}
