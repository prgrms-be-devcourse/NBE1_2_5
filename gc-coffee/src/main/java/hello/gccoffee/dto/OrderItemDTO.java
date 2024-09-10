package hello.gccoffee.dto;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

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

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String postcode;

    @NotBlank
    private String productName;

    @NotBlank
    @Min(0)
    private int price;

    @NotBlank
    @Min(0)
    @Max(100)
    private int quantity;

    @NotBlank
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
