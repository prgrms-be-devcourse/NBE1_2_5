package hello.gccoffee.serviceTest;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Category;
import hello.gccoffee.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @Commit
    public void registerTest() {
        ProductDTO productDTO = ProductDTO.builder()
                .productId(1)
                .price(8000)
                .category(Category.COFFEE_1)
                .description("맛있는 커피3")
                .build();

        productService.modify(productDTO);
    }
}
