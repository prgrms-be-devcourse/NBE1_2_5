package hello.gccoffee.service;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO register(ProductDTO productDTO){
        try {

        Product product = productDTO.toEntity();
        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct);

        }catch (Exception e){
            throw ProductException.FAIL_REGISTER.get();
        }
    }
}
