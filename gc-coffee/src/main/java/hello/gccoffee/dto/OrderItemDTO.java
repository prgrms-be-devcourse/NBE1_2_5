package hello.gccoffee.dto;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "주문 상품 DTO")
public class OrderItemDTO {

    // description 필요 시 추가
//    private int productId;

    @Schema(description = "주문 번호", example = "1")
    private Integer orderId;

    @Parameter(name = "orderItemId", description = "주문한 상품 번호", example = "1")
    private Integer orderItemId;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    @Schema(description = "주문자 이메일", example = "hong@gmail.com")
    private String email;

    @NotBlank(message = "주소를 입력해주세요")
    @Schema(description = "주문자 주소", example = "서울시 강남구")
    private String address;

    @NotBlank(message = "우편번호를 입력해주세요")
    @Schema(description = "주문자 우편번호", example = "15888")
    private String postcode;

    @NotBlank(message = "상품이름을 입력해주세요")
    @Schema(description = "상품 이름", example = "Columbia Coffee")
    private String productName;

    @NotNull(message = "가격을 입력해주세요")
    @Min(0)
    @Max(1_000_000)
    @Schema(description = "상품 가격", example = "10000" )
    private int price;

    @NotNull(message = "수량을 입력해주세요")
    @Min(0)
    @Max(100)
    @Schema(description = "상품 수량", example = "3")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "카테고리를 입력해주세요")
    @Schema(description = "카테고리")
    private Category category;

    public OrderItemDTO(OrderItem orderItem) {
        orderId = orderItem.getOrderItemId();
        orderItemId = orderItem.getOrderItemId();
        email = orderItem.getOrder().getEmail();
        address = orderItem.getOrder().getAddress();
        postcode = orderItem.getOrder().getPostcode();
        productName = orderItem.getProduct().getProductName();
        price = orderItem.getPrice();
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
