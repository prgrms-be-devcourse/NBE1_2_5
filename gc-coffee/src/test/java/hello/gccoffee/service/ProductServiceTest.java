package hello.gccoffee.service;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Order;
import hello.gccoffee.entity.OrderItem;
import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.repository.OrderRepository;
import hello.gccoffee.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static hello.gccoffee.entity.Category.COFFEE_BEAN_CAPSULE;
import static hello.gccoffee.entity.Category.COFFEE_BEAN_PACKAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testRemoveProductSuccessfully() {
        // Given
        int productId = 8;
        ProductDTO productDTO = ProductDTO.builder()
                .productId(productId)
                .productName("Test Product")
                .category(COFFEE_BEAN_PACKAGE) // 예시 카테고리
                .price(5000)
                .description("This is a test product")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // ProductDTO를 엔티티로 변환하여 저장
        Product product = productDTO.toEntity();
        productRepository.save(product);
        // 다른 필드들도 적절히 설정
        productRepository.save(product); // DB에 제품 저장

        // When
        productService.remove(productId);

        // Then
        assertFalse(productRepository.findById(productId).isPresent(), "Product should not be present");
    }

    @Test
    @Commit
    public void registerData(){
        IntStream.rangeClosed(1, 5).forEach(i -> {
            //Given
            // Product 데이터 생성
            if (i<=2) {
                Product product = Product.builder()
                        .productName(i%2==1 ? "AMERICANO" : "LATTE")
                        .category(i%2==1 ? COFFEE_BEAN_PACKAGE : COFFEE_BEAN_CAPSULE) // 예시 카테고리
                        .price(i%2==1 ? 2000 : 3500)
                        .description(i%2==1 ? "Test Product1" : "Test Product2")
                        .build();

                productRepository.save(product);
            }
            Product product = productRepository.findById(i<3?1:2).orElseThrow(ProductException.NOT_FOUND::get);

            //Order 데이터 생성
            Order order = Order.builder()
                    .email("abc@abc"+i+".com")
                    .address("address"+i)
                    .postcode("postcode"+i)
                    .build();

            //OrderItem 데이터 생성
            OrderItem orderItem =
                    OrderItem.builder()
                            .order(order)
                            .product(product)
                            .category(product.getCategory())
                            .price(product.getPrice())
                            .quantity(i<3?2:3)
                            .build();
            //when
            order.addOrderItems(orderItem);

            orderRepository.save(order);
            log.info("[product Entity] = "+product);
            log.info("[Order Entity] = "+order);
            log.info("[OrderItem Entity] = "+orderItem);

            //then
            assertNotNull(product);
            assertNotNull(order);
            assertNotNull(orderItem);
        });
    }

}