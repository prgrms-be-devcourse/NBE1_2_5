package hello.gccoffee.dto;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Product;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "상품 정보 DTO")
public class ProductDTO {

    @Schema(description = "상품 번호")
    private Integer productId;

    @NotBlank(message = "상품 이름을 입력해 주세요.")
    @Schema(description = "상품 이름", example = "Columbia Coffee")
    private String productName;

    @NotNull(message = "*카테고리는 필수 입력 값입니다.")
    @Schema(description = "카테고리")
    private Category category;

    @NotNull(message = "*가격은 필수 입력 값입니다.")
    @Range(min = 1000, max = 100_000, message = "*1000원 이상, 100,000원 이하로 등록 부탁드립니다.")
    @Schema(description = "상품 가격", example = "10000")
    private Integer price;

    @Schema(description = "상품 설명", example = "콜롬비아의 맛있는 커피")
    private String description;

    @Schema(description = "상품 등록 일자")
    private LocalDateTime createdAt;

    @Schema(description = "상품 수정 일자")
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