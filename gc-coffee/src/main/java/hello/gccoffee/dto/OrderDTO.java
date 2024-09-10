package hello.gccoffee.dto;

import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    @Email
    private String email;

    @NotBlank
    private String postcode;

    @NotBlank
    private String address;

    private OrderEnum orderEnum;

    private int orderId;

    public OrderDTO(Order order) {
        email = order.getEmail();
        postcode = order.getPostcode();
        address = order.getAddress();
        orderEnum = order.getOrderEnum();
        orderId = order.getOrderId();
    }

    public Order toEntity() {
        return Order.builder()
                .orderId(orderId)
                .email(email)
                .address(address)
                .postcode(postcode)
                .build();
    }
}
