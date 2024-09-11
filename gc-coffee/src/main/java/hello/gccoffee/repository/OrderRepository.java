package hello.gccoffee.repository;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // 이메일에 해당하면서, 완료처리 되지 않은 주문 조회
    @Query("SELECT o FROM Order o WHERE o.email = :email AND o.orderEnum = 'ORDER_ACCEPTED'")
    Optional<Order> findByEmail(String email);

    List<Order> findAllByEmail(String email);

    @Query("SELECT o FROM Order o JOIN o.orderItems oi WHERE o.email = :email AND o.orderId = :orderId AND oi.orderItemId = :orderItemId")
    Optional<Order> findByEmailAndOrderIdAndOrderItemId(@Param("email") String email, @Param("orderId") int orderId, @Param("orderItemId") int orderItemId);

    void deleteByEmail(String email);

    @Query("SELECT o FROM Order o WHERE o.email = :email AND o.orderEnum = 'ORDER_ACCEPTED'")
    Optional<List<Order>> findByEmails(String email);
}
