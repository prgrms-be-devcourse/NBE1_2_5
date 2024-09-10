package hello.gccoffee.repository;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testRegister() {
        IntStream.range(1, 11).forEach(i -> {
            Product product = Product.builder()
                    .productName("커피_" + i)
                    .category(Category.COFFEE_1)
                    .price(i * 1000)
                    .description("설명_" + i)
                    .build();

            Product savedProduct = productRepository.save(product);

            assertNotNull(savedProduct);
            assertEquals(i, savedProduct.getProductId());
            assertEquals("커피_" + i, savedProduct.getProductName()); // 첫 번째 이미지 번호 검증
            assertEquals(i * 1000, savedProduct.getPrice());
        });
    }
}