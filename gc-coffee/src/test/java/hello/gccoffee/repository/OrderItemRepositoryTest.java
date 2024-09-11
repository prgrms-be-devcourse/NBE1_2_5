package hello.gccoffee.repository;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hello.gccoffee.entity.Category.COFFEE_BEAN_PACKAGE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class OrderItemRepositoryTest {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

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

    @Test
    public void jPQLOrderItemTest(){
        String productName = "아메리카노";
        Category category = COFFEE_BEAN_PACKAGE;
        int price = 1111;
        int quantity = 1;
        Product productId = productRepository.findByProductName(productName).get();
        log.info(productId);
    }
    @Test
    public void jPQLOrderItemTest2(){
        Product product = Product.builder().productId(1)
                        .productName("아메리카노").category(COFFEE_BEAN_PACKAGE)
                        .price(2000).description("맛있는커피").build();
        log.info(orderItemRepository.findProductIdByOrderItemId(product, 1, 1111));
    }
}
