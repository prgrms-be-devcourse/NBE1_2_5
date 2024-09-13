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
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSave() {
        IntStream.range(1, 11).forEach(i -> {
            Product product = Product.builder()
                    .productName("커피 캡슐 " + i)
                    .category(Category.COFFEE_BEAN_CAPSULE)
                    .price(10000)
                    .description("캡슐입니다.")
                    .build();
            Product savedProduct = productRepository.save(product);

            assertNotNull(savedProduct);
            assertEquals("커피 캡슐 " + i, savedProduct.getProductName());
        });
    }
}