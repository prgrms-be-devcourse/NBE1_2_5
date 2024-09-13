package hello.gccoffee.repository;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import lombok.extern.log4j.Log4j2;
import hello.gccoffee.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @Autowired
    private OrderRepository orderRepository;

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
        Product productId = productRepository.findByProductName(productName);
        log.info(productId);
    }

    @Test
    public void jPQLOrderItemTest2(){
        Product product = Product.builder().productId(1)
                        .productName("아메리카노").category(COFFEE_BEAN_PACKAGE)
                        .price(2000).description("맛있는커피").build();
        log.info(orderItemRepository.findProductIdByOrderItemId(product, 1, 1111));
    }

    @Test
    public void testOrderSave() {
        IntStream.range(1, 11).forEach(i -> {
            Order order = Order.builder()
                    .email(i + "@aaa.com")
                    .address("서울시")
                    .postcode("11111")
                    .orderEnum(OrderEnum.ORDER_ACCEPTED)
                    .build();
            Order savedOrder = orderRepository.save(order);

            assertNotNull(savedOrder);
            assertEquals(i + "@aaa.com", savedOrder.getEmail());
        });
    }

    @Test
    public void testOrderItemSave() {
        IntStream.range(1, 2).forEach(i -> {
            Optional<Order> order = orderRepository.findById(2);
            Optional<Product> product = productRepository.findById(3);
            OrderItem orderItem = OrderItem.builder()
                    .order(order.get())
                    .product(product.get())
                    .category(product.get().getCategory())
                    .price(product.get().getPrice())
                    .quantity(14)
                    .build();

            orderItemRepository.save(orderItem);

            assertNotNull(orderItem);
        });
    }
}
