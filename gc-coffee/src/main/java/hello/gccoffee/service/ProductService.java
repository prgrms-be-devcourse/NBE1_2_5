package hello.gccoffee.service;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.Optional;


import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;


    public ProductDTO read(Long pno) {     //상품 조회
        Product product = productRepository.findById(pno).orElseThrow(ProductException.NOT_FOUND::get);
        return new ProductDTO(product);
    }

    public ProductDTO modify(ProductDTO productDTO) {
        if (productDTO == null) throw ProductException.NOT_FOUND.get();
        Optional<Product> foundProduct = productRepository.findById(productDTO.getProductId());
        Product product = foundProduct.orElseThrow(ProductException.NOT_FOUND::get);

        log.info(product.toString());
        try {
            product.changePrice(productDTO.getPrice());
            product.changeCategory(productDTO.getCategory());
            product.changeDescription(productDTO.getDescription());
            productRepository.save(product);

            return new ProductDTO(product);
        } catch (Exception e) {
            throw ProductException.FAIL_MODIFY.get();
        }
    }
}