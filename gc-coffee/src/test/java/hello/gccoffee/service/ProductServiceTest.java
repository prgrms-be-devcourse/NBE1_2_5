package hello.gccoffee.service;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testRemoveProductSuccessfully() {
        // Given
        int productId = 8;
        ProductDTO productDTO = ProductDTO.builder()
                .productId(productId)
                .productName("Test Product")
                .category(Category.COFFEE_BEAN_PACKAGE) // 예시 카테고리
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


}