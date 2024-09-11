package hello.gccoffee.repository;

import hello.gccoffee.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    // t_order 테이블의 orderId를 매개변수로 받아 해당 orderId와 일치하는 상품을 리스트로 반환
    @Query("SELECT o From OrderItem o WHERE o.order.orderId = :orderId")
    Optional<List<OrderItem>> findByOrderId(@Param("orderId") int orderId);

    void deleteByOrderOrderId(int orderId);
}
