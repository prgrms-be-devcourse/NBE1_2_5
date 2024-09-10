package hello.gccoffee.repository;

import hello.gccoffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByEmail(String email);
}
