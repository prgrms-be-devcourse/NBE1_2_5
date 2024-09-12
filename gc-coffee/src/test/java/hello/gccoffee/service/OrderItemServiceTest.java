package hello.gccoffee.service;

import hello.gccoffee.dto.OrderItemDTO;
import hello.gccoffee.entity.*;
import hello.gccoffee.exception.OrderTaskException;
import hello.gccoffee.repository.OrderItemRepository;
import hello.gccoffee.repository.OrderRepository;
import hello.gccoffee.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class OrderItemServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("OrderItem의 수량을 10에서 5로 수정")
    public void testUpdateOrderItem() {
        Order order = Order.builder()
                .email("1@aaa.com")
                .address("서울시")
                .postcode("11111")
                .orderEnum(OrderEnum.ORDER_ACCEPTED)
                .build();
        orderRepository.save(order);
        Product product = Product.builder()
                .productName("커피 캡슐 1")
                .category(Category.COFFEE_BEAN_CAPSULE)
                .price(10000)
                .description("캡슐입니다.")
                .build();
        productRepository.save(product);
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .category(product.getCategory())
                .price(product.getPrice())
                .quantity(10)
                .build();
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        log.info("savedOrderItem = " + savedOrderItem);

        OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                .orderItemId(savedOrderItem.getOrderItemId())
                .email(order.getEmail())
                .address(order.getAddress())
                .postcode(order.getPostcode())
                .productName("커피 캡슐 2")
                // price, category @NotNull 제거 필요. 또는 요청 용 OrderItemReqeustDTO를 따로 생성
                .price(10000)
                .category(Category.COFFEE_BEAN_CAPSULE)
                .quantity(5)    // 수량을 10 -> 5로 수정
                .build();
        OrderItemDTO updatedOrderItemDTO = orderItemService.updateOrderItem(orderItemDTO);
        log.info("updatedOrderItemDTO = " + updatedOrderItemDTO);

        assertNotNull(updatedOrderItemDTO);
        assertEquals("커피 캡슐 2", updatedOrderItemDTO.getProductName());
        assertEquals(5, updatedOrderItemDTO.getQuantity());
    }

    @Test
    @DisplayName("OrderItem의 수량을 10에서 0으로 수정할 경우 예외")
    public void testUpdateOrderItemQuantityZero() {
        Order order = Order.builder()
                .email("1@aaa.com")
                .address("서울시")
                .postcode("11111")
                .orderEnum(OrderEnum.ORDER_ACCEPTED)
                .build();
        orderRepository.save(order);
        Product product = Product.builder()
                .productName("커피 캡슐 1")
                .category(Category.COFFEE_BEAN_CAPSULE)
                .price(10000)
                .description("캡슐입니다.")
                .build();
        productRepository.save(product);
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .category(product.getCategory())
                .price(product.getPrice())
                .quantity(10)
                .build();
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        log.info("savedOrderItem = " + savedOrderItem);

        OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                .orderItemId(savedOrderItem.getOrderItemId())
                .email(order.getEmail())
                .address(order.getAddress())
                .postcode(order.getPostcode())
                .productName("커피 캡슐 2")
                // price, category @NotNull 제거 필요. 또는 요청 용 OrderItemReqeustDTO를 따로 생성
                .price(10000)
                .category(Category.COFFEE_BEAN_CAPSULE)
                .quantity(0)    // 수량을 10 -> 0으로 수정
                .build();
        assertThrows(OrderTaskException.class, () ->
                orderItemService.updateOrderItem(orderItemDTO), "Invalid Order Item Quantity");
    }
}