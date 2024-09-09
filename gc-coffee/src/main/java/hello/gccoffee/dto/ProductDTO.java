package hello.gccoffee.dto;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private int productId;

    private String productName;

    private Category category;

    private int price;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ProductDTO(Product product) {
        productId = product.getProductId();
        productName = product.getProductName();
        category = product.getCategory();
        price = product.getPrice();
        description = product.getDescription();
        createdAt = product.getCreatedAt();
        updatedAt = product.getUpdatedAt();
    }

    public Product toEntity() {
        return Product.builder()
                .productId(productId)
                .productName(productName)
                .category(category)
                .price(price)
                .description(description)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
