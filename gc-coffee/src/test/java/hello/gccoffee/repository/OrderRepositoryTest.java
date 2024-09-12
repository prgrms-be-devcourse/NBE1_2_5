package hello.gccoffee.repository;

import hello.gccoffee.entity.OrderEnum;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    @Commit
    public void updateTest() {
        int order = orderRepository.countOrderByAccepted(); // 배송되지 않은 주문 수
        log.info("order: " + order);

        int update = orderRepository.updateOrderStatus(OrderEnum.SHIPPED); // 배송상태로 변경된 주문 수
        log.info("update: " + update);

        assertEquals(order, update);
    }
}
