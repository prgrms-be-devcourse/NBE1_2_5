package hello.gccoffee.repository;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
    // t_order 테이블의 orderId와 t_prod 테이블의 productId를 매개변수로 받아
    // 해당 orderId, productId와 일치하는 상품을 리스트로 반환
    @Query("SELECT o From OrderItem o  " +
            "WHERE o.order.orderId = :orderId")
    Optional<List<OrderItem>> findByOrderId(@Param("orderId") int orderId);

    @Query("SELECT o.orderItemId FROM OrderItem o join o.product p  WHERE o.product=:product and o.quantity=:quantity and o.price=:price")
    Optional<List<Integer>> findProductIdByOrderItemId(@Param("product") Product product, @Param("quantity") int quantity, @Param("price") int price);

}
