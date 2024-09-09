package hello.gccoffee.dto;

import hello.gccoffee.entity.Category;
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
        this.email = orderItem.getEmail();
        this.address = orderItem.getAddress();
        this.postcode = orderItem.getPostCode();
        this.productName = orderItem.getProduct().getProductName();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
        this.category = orderItem.getCategory();
    }
    public OrderItem toEntity() {
        Product product = Product.builder().productName(productName).build();
        return OrderItem.builder()
                .email( this.email)
                .address( this.address)
                .postCode(this.postcode)
                .product(product)
                .price(this.price)
                .quantity( this.quantity)
                .category(this.category)
                .build();
    }
}
