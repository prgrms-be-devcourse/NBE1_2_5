package hello.gccoffee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_order")
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private String email;

    private String address;

    private String postcode;

    @CreatedDate
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderEnum orderEnum = OrderEnum.ORDER_ACCEPTED;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItems(OrderItem orderItem) {
        if (orderItems.contains(orderItem)) return;
        orderItems.add(orderItem);
    }

    public void clearOrderItems() {
        orderItems.clear();
    }

    public void changeOrderEnum(OrderEnum orderEnum) {
        this.orderEnum = orderEnum;
    }
}
