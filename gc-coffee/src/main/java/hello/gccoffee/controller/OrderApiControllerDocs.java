package hello.gccoffee.controller;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.dto.OrderItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Tag(name = "User", description = "사용자 기능에 대한 API")
public interface OrderApiControllerDocs {

    @Operation(summary = "주문 추가", description = "새로운 주문을 추가합니다. 주문이 성공적으로 추가되면, 해당 주문과 함께 배송 시작 안내 메시지를 포함한 응답을 반환합니다.")
    ResponseEntity<Map<String, Object>> addOrder(
            @Parameter(description = "주문 정보", required = true) @Validated @RequestBody OrderDTO orderDTO,
            @Parameter(hidden = true) BindingResult bindingResult
    );

    @Operation(summary = "주문 아이템 추가", description = "주문 ID와 함께 새로운 주문 아이템을 추가합니다.")
    ResponseEntity<OrderDTO> addOrderItems(
            @Parameter(name = "orderId", description = "주문 ID", required = true) @PathVariable Integer orderId,
            @Parameter(description = "추가할 주문 아이템 목록", required = true) @Validated @RequestBody List<OrderItemDTO> items,
            @Parameter(hidden = true) BindingResult bindingResult
    );

    @Operation(summary = "주문 목록 조회", description = "사용자의 이메일을 입력받아 해당 사용자의 모든 주문 항목을 조회합니다.")
    ResponseEntity<List<OrderItemDTO>> readOrder(
            @Parameter(name = "email", description = "사용자의 이메일 주소", required = true) @RequestParam("email") String email
    );

    @Operation(summary = "주문 아이템 수정", description = "주문 ID와 주문 아이템 ID를 사용하여 주문 아이템의 세부 사항을 수정합니다.")
    ResponseEntity<OrderItemDTO> updateSingleOrderItem(
            @Parameter(name = "email", description = "사용자의 이메일 주소", required = true) @RequestParam("email") String email,
            @Parameter(name = "orderId", description = "주문 ID", required = true) @PathVariable Integer orderId,
            @Parameter(name = "orderItemId", description = "주문 아이템 ID", required = true) @PathVariable Integer orderItemId,
            @Parameter(description = "수정할 주문 아이템 정보", required = true) @Validated @RequestBody OrderItemDTO orderItemDTO
    );

    @Operation(summary = "주문 아이템 삭제", description = "주문 ID와 주문 아이템 ID를 사용하여 특정 주문 아이템을 삭제합니다.")
    ResponseEntity<Map<String, String>> deleteOrder(
            @Parameter(name = "email", description = "사용자의 이메일 주소", required = true) @RequestParam("email") String email,
            @Parameter(name = "orderId", description = "주문 ID", required = true) @PathVariable Integer orderId,
            @Parameter(name = "orderItemId", description = "주문 아이템 ID", required = true) @PathVariable Integer orderItemId
    );
}

