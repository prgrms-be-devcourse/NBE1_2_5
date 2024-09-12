package hello.gccoffee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_order_item")
@Getter
@ToString(exclude = {"order", "product"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "주문 상품 Entity")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "주문 상품 ID")
    private int orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    @Schema(description = "주문 정보")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    @Schema(description = "상품 정보")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Schema(description = "카테고리")
    private Category category;

    @Schema(description = "상품 가격")
    private Integer price;

    @Schema(description = "상품 수량")
    private Integer quantity;

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void changeProduct(Product product) {
        this.product = product;
    }

    public void changeOrder(Order order) {
        this.order = order;
    }

    public void changeQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
