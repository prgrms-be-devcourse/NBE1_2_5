package hello.gccoffee.dto;

import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderEnum;

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
public class OrderDTO {

    @Email
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
