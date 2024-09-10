package hello.gccoffee.repository;

import hello.gccoffee.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrderItemRepositoryTest {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @Transactional
    public void testOrderRead() {
        int orderId = 1;

        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId).orElse(null);
        orderItemList.forEach(orderItem -> {
            System.out.println("orderItem : " + orderItem);
            System.out.println("=======================");
            System.out.println("orderItem.getOrder().getOrderId() : " + orderItem.getOrder().getOrderId());
        });
    }
}
