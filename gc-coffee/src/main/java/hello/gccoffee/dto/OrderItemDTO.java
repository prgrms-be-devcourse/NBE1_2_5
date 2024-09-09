package hello.gccoffee.dto;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class OrderItemDTO {
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
    private int quantity;

    @NotBlank
    private Category category;

    public OrderItemDTO(OrderItem orderItem) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    public OrderItem toEntity() {
        Product product = Product.builder().productName(productName).build();
        Order order = Order.builder()
                .email(this.email)
                .address( this.address)
                .postcode(this.postcode)
                .product(product)
                .price(this.price)
                .quantity( this.quantity)
                .category(this.category)
                .build();
    }
}
