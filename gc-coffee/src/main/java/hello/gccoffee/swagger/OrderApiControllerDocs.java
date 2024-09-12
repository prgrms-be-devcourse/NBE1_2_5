package hello.gccoffee.swagger;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.exception.OrderException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Tag(name = "User", description = "주문자의 주문 CRUD 관련 API")
public interface OrderApiControllerDocs {

    @Operation(summary = "주문 등록", description = "새로운 주문을 추가합니다. 주문이 성공적으로 등록되면, 해당 주문과 함께 배송 시작 안내 메시지를 포함한 응답을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "주문 등록 완료")
    @OrderExceptionCode({OrderException.INVALID_EMAIL, OrderException.INVALID_RESOURCE, OrderException.ORDER_NOT_REGISTERED})
    ResponseEntity<Map<String, Object>> addOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "주문 정보", required = true) @Validated @RequestBody OrderDTO orderDTO,
            @Parameter(hidden = true) BindingResult bindingResult
    );

    @Operation(summary = "주문 상품 등록", description = "주문 번호와 함께 새로운 주문 상품을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "주문 상품 등록 완료")
    @OrderExceptionCode({OrderException.INVALID_RESOURCE, OrderException.ORDER_LIST_ALREADY_EXISTS})
    ResponseEntity<OrderDTO> addOrderItems(
            @Parameter(description = "주문 번호") @PathVariable Integer orderId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "추가할 주문 상품 목록") @Validated @RequestBody List<OrderItemDTO> items,
            @Parameter(hidden = true) BindingResult bindingResult
    );

    @Operation(summary = "주문 목록 조회", description = "주문자의 이메일을 입력받아 해당 주문자의 모든 주문 항목을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "주문 목록 조회 완료")
    @OrderExceptionCode({OrderException.INVALID_EMAIL, OrderException.MISSING_EMAIL, OrderException.ORDER_NOT_FOUND})
    ResponseEntity<List<OrderItemDTO>> readOrder(
            @Parameter(description = "주문자의 이메일 주소") @RequestParam("email") String email
    );

    @Operation(summary = "주문 상품 수정", description = "주문 번호와 주문 상품 번호를 사용하여 주문 상품의 세부 사항을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "주문 상품 수정 완료")
    @OrderExceptionCode({
            OrderException.INVALID_EMAIL, OrderException.MISSING_EMAIL, OrderException.ORDER_NOT_FOUND,
            OrderException.ORDER_ITEM_NOT_FOUND, OrderException.ORDER_ITEM_NOT_UPDATED, OrderException.INVALID_RESOURCE})
    ResponseEntity<OrderItemDTO> updateSingleOrderItem(
            @Parameter(description = "주문자의 이메일 주소") @RequestParam("email") String email,
            @Parameter(description = "주문 번호") @PathVariable Integer orderId,
            @Parameter(description = "주문 상품 번호") @PathVariable Integer orderItemId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정할 주문 상품 정보") @Validated @RequestBody OrderItemDTO orderItemDTO
    );

    @Operation(summary = "주문 상품 삭제", description = "주문 번호와 주문 상품 번호를 사용하여 특정 주문 상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "주문 상품 삭제 완료")
    @OrderExceptionCode({
            OrderException.ORDER_NOT_FOUND, OrderException.ORDER_ITEM_NOT_FOUND, OrderException.INVALID_EMAIL,
            OrderException.INVALID_RESOURCE, OrderException.ORDER_NOT_REMOVED, OrderException.ORDER_ITEM_NOT_REMOVED})
    ResponseEntity<Map<String, String>> deleteOrder(
            @Parameter(description = "주문자의 이메일 주소") @RequestParam("email") String email,
            @Parameter(description = "주문 번호") @PathVariable Integer orderId,
            @Parameter(description = "주문 상품 번호") @PathVariable Integer orderItemId
    );
}

