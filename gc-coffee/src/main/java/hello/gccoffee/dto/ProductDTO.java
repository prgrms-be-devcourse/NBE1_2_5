package hello.gccoffee.dto;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Integer productId;

    @NotBlank(message = "상품 이름을 입력해 주세요.")
    private String productName;

    @NotNull(message = "*카테고리는 필수 입력 값입니다.")
    private Category category;

    @NotNull(message = "*가격은 필수 입력 값입니다.")
    @Range(min = 1000, max = 100_000, message = "*1000원 이상, 100,000원 이하로 등록 부탁드립니다.")
    private Integer price;

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
