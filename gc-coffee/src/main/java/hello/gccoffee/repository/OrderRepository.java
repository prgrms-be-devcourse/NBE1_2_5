package hello.gccoffee.repository;

import hello.gccoffee.dto.OrderDTO;
import hello.gccoffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByEmail(String email);

    List<Order> findAllByEmail(String email);
    void deleteByEmail(String email);
}
