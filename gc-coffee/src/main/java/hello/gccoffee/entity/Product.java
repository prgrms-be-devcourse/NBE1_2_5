package hello.gccoffee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_prod")
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Schema(description = "상품 정보 Entity")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "상품 번호")
    private Integer productId;

    @Column(length = 100)
    @Schema(description = "상품 이름")
    private String productName;

    @Enumerated(EnumType.STRING)
    @Schema(description = "상품 종류")
    private Category category;

    @Schema(description = "상품 가격")
    private Integer price;

    @Column(length = 2000)
    @Schema(description = "상품 설명")
    private String description;

    @CreatedDate
    @Schema(description = "상품 등록 일자")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Schema(description = "상품 수정 일자")
    private LocalDateTime updatedAt;

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeProductName(String productName) {
        this.productName = productName;
    }
}
