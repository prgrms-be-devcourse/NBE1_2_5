package hello.gccoffee.swagger;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Tag(name = "Admin", description = "관리자의 주문 URD 및 상품 CRUD 관련 API")
public interface AdminApiControllerDocs {

    @Operation(summary = "상품 등록", description = "판매할 상품 정보를 입력받아 새로운 상품을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "상품 등록 성공")
    ResponseEntity<ProductDTO> registerProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "등록할 상품 정보") @Validated @RequestBody ProductDTO productDTO);

    @Operation(summary = "상품 조회", description = "상품 번호를 입력받아 해당하는 상품을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 조회 성공")
    ResponseEntity<ProductDTO> readProduct(
            @Parameter(description = "상품 번호") @PathVariable("productId") Integer productId);

    @Operation(summary = "상품 수정", description = "관리자가 등록된 상품의 세부 사항을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "상품 수정 성공")
    ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "상품 번호") @PathVariable("productId") Integer productId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정할 상품 정보") @Validated @RequestBody ProductDTO productDTO);

    @Operation(summary = "상품 삭제", description = "관리자 비밀번호를 입력받아 관리자임을 인증하고, 상품 번호를 사용하여 해당 상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "상품 삭제 성공")
    ResponseEntity<Map<String, String>> removeProduct(
            @Parameter(description = "상품 번호") @PathVariable("productId") Integer productId,
            @Parameter(description = "관리자 비밀번호") @RequestParam("adminPassword") String adminPassword);

    @Operation(summary = "주문 목록 조회", description = "관리자 비밀번호를 입력받아 관리자임을 인증하고, 전체 주문 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공")
    ResponseEntity<List<OrderItemDTO>> getOrderList(
            @Parameter(description = "관리자 비밀번호") @RequestParam("adminPassword") String adminPassword);

    @Operation(summary = "주문 내역 수정", description = "관리자 비밀번호를 입력받아 관리자임을 인증하고, 주문 번호와 변경할 내용을 입력받아 해당 주문을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "주문 내역 수정 성공")
    ResponseEntity<OrderItemDTO> updateOrderItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정할 주문 정보") @Validated @RequestBody OrderItemDTO orderItemDTO,
            @Parameter(description = "주문 상품 번호") @PathVariable Integer orderItemId,
            @Parameter(description = "관리자 비밀번호") @RequestParam("adminPassword") String adminPassword);

    @Operation(summary = "주문 내역 단일 삭제", description = "관리자 비밀번호를 입력받아 관리자임을 인증하고, 주문자의 이메일과 주문 번호를 받아 해당하는 단일 주문을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "주문 내역 단일 삭제 성공")
    ResponseEntity<Map<String, String>> deleteOneOrder(
            @Parameter(description = "주문 번호") @PathVariable Integer orderId,
            @Parameter(description = "주문자의 이메일") @RequestParam String email,
            @Parameter(description = "관리자 비밀번호") @RequestParam("adminPassword") String adminPassword);

    @Operation(summary = "주문 내역 전체 삭제", description = "주문자의 이메일을 받아 해당하는 전체 주문을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "주문 내역 전체 삭제 성공")
    ResponseEntity<Map<String, String>> deleteAllOrder(
            @Parameter(description = "주문자의 이메일") @RequestParam String email,
            @Parameter(description = "관리자 비밀번호") @RequestParam("adminPassword") String adminPassword);
}
