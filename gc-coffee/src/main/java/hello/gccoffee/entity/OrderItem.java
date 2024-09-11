package hello.gccoffee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_order_item")
@Getter
@ToString(exclude = {"order", "product"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer price;

    private int quantity;

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
}
