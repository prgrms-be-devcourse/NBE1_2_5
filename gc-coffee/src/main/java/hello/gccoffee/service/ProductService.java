package hello.gccoffee.service;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Product;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
//지워야함
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(ProductDTO productDTO) {

        Product product = productDTO.toEntity();
        productRepository.save(product);
        return product;
    }

    public Product findById(int productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findByName(String productName) {
        return productRepository.findByProductName(productName);
    }
}
