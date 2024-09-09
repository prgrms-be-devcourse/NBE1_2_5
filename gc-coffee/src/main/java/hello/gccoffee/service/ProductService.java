package hello.gccoffee.service;

import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Log4j2
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void remove(int pno) {
        Optional<Product> foundProduct = productRepository.findById(pno);
        Product product = foundProduct.orElseThrow(ProductException.NOT_FOUND::get);

        try {
            productRepository.delete(product);
        } catch(Exception e) {
            log.error("--- " + e.getMessage());
            throw ProductException.NOT_REMOVED.get();
        }
    }
}
