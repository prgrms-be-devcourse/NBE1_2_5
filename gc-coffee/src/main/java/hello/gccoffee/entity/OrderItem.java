package hello.gccoffee.entity;

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
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int price;

    private int quantity;
}
