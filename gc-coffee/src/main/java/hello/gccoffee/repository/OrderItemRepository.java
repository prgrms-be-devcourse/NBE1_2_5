package hello.gccoffee.repository;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    // orderId를 매개변수로 받아 orderId와 일치하는 상품을 리스트로 반환
    @Query("SELECT o From OrderItem o  WHERE o.order.orderId = :orderId")
    Optional<List<OrderItem>> findByOrderId(@Param("orderId") int orderId);

    @Modifying
    @Query("delete from OrderItem o where o.product.productName = :productName and o.price=:price and o.category = :category")
    int deleteByProductNameAndCategoryAndPrice(@Param("price") int price, @Param("category") Category category, @Param("productName") String productName);

    void deleteByOrderOrderId(int orderId);

    @Query("SELECT o.orderItemId FROM OrderItem o join o.product p  WHERE o.product=:product and o.quantity=:quantity and o.price=:price")
    Optional<List<Integer>> findProductIdByOrderItemId(@Param("product") Product product, @Param("quantity") int quantity, @Param("price") int price);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderItem oi WHERE oi.order.email = :email AND oi.order.orderId = :orderId AND oi.orderItemId = :orderItemId")
    void deleteByEmailAndOrderIdAndOrderItemId(@Param("email") String email, @Param("orderId") int orderId, @Param("orderItemId") int orderItemId);

}
