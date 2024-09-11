package hello.gccoffee.repository;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // 이메일에 해당하면서, 완료처리 되지 않은 주문 조회
    @Query("SELECT o FROM Order o WHERE o.email = :email AND o.orderEnum = 'ORDER_ACCEPTED'")
    Optional<Order> findByEmail(String email);

    List<Order> findAllByEmail(String email);
    void deleteByEmail(String email);
}
