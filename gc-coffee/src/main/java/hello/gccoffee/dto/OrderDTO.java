package hello.gccoffee.dto;

import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
@Schema(description = "주문 정보 DTO")
public class OrderDTO {

    @Email
    @Schema(description = "사용자 이메일", example = "hong@gmail.com")
    private String email;

    @NotBlank
    private String postcode;

    @NotBlank
    private String address;

    private OrderEnum orderEnum;

    private Integer orderId;

    public OrderDTO(Order order) {
        email = order.getEmail();
        postcode = order.getPostcode();
        address = order.getAddress();
        orderEnum = order.getOrderEnum();
        orderId = order.getOrderId();
    }

    public Order toEntity() {
        return Order.builder()
                .email(email)
                .address(address)
                .postcode(postcode)
                .build();
    }
}
