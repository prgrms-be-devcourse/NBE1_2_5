package hello.gccoffee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "주문 정보 Entity")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "주문 ID")
    private int orderId;

    @Schema(description = "주문자 이메일")
    private String email;

    @Schema(description = "주문자 주소")
    private String address;

    @Schema(description = "주문자 우편번호")
    private String postcode;

    @CreatedDate
    @Schema(description = "주문 시간")
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Schema(description = "주문 상태")
    private OrderEnum orderEnum = OrderEnum.ORDER_ACCEPTED;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @Schema(description = "주문 상품 정보")
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
