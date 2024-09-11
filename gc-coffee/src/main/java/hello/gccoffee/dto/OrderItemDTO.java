package hello.gccoffee.dto;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    // description 필요 시 추가
//    private int productId;
//    private int orderId;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @NotBlank(message = "우편번호를 입력해주세요")
    private String postcode;

    @NotBlank(message = "상품이름을 입력해주세요")
    private String productName;

    @NotNull
    @Min(0)
    @Max(1_000_000)
    private int price;

    @NotNull
    @Min(0)
    @Max(100)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    public OrderItemDTO(OrderItem orderItem) {
        email = orderItem.getOrder().getEmail();
        address = orderItem.getOrder().getAddress();
        postcode = orderItem.getOrder().getPostcode();
        productName = orderItem.getProduct().getProductName();
        price = orderItem.getProduct().getPrice();
        quantity = orderItem.getQuantity();
        category = orderItem.getCategory();
    }

    public OrderItem toEntity(int productId, int orderId) {
        Product product = Product.builder()
                .productId(productId)
                .productName(productName)
                .category(category)
                .price(price)
                .build();

        Order order = Order.builder()
                .orderId(orderId)
                .email(this.email)
                .address( this.address)
                .postcode(this.postcode)
                .build();

        return OrderItem.builder()
                .order(order)
                .product(product)
                .category(category)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
