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
    private Integer orderId;

    private String email;

    private String address;

    private String postcode;

    @CreatedDate
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderEnum orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItems(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public void clearOrderItems() {
        orderItems.clear();
    }

    public void changeOrderStatus(OrderEnum orderStatus) {
        this.orderStatus = orderStatus;
    }
}
