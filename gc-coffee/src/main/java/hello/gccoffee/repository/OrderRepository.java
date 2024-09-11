package hello.gccoffee.repository;

import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // 이메일에 해당하면서, 완료처리 되지 않은 주문 조회
    @Query("SELECT o FROM Order o WHERE o.email = :email AND o.orderEnum = 'ORDER_ACCEPTED'")
    Optional<Order> findByEmail(String email);

    // 배송 되지 않은 주문의 수 조회
    @Query("SELECT COUNT(*) FROM Order o WHERE o.orderEnum = 'ORDER_ACCEPTED'")
    int countOrderByAccepted();

    // 주문 상태가 ORDER_ACCEPTED인 주문들의 상태 변경
    @Modifying(flushAutomatically = true) // select문이 아님을 명시. executeUpdate()
    @Query("UPDATE Order o set o.orderEnum = :status WHERE o.orderEnum = 'ORDER_ACCEPTED'")
    int updateOrderStatus(OrderEnum status);


    @Query("SELECT o FROM Order o WHERE o.email = :email AND o.orderEnum = 'ORDER_ACCEPTED'")
    Optional<List<Order>> findByEmails(String email);

    List<Order> findAllByEmail(String email);

    @Query("SELECT o.orderId FROM Order o WHERE o.email = :email")
    List<Integer> findOrderIdByEmail(@Param("email") String email);

}
