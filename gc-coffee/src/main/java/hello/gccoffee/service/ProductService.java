package hello.gccoffee.service;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.exception.ProductTaskException;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO register(ProductDTO productDTO) {
        Product product = productDTO.toEntity();
        try {
            productRepository.save(product);
            return new ProductDTO(product);
        } catch (ProductTaskException e) {
            log.error("---" + e.getMessage());
            throw ProductException.PRODUCT_NOT_REGISTERED.get();
        }
    }

    public ProductDTO read(int pno) {     //상품 조회
        Product product = productRepository.findById(pno).orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);
        return new ProductDTO(product);
    }

    public ProductDTO modify(ProductDTO productDTO) {
        if (productDTO == null) throw ProductException.PRODUCT_NOT_FOUND.get();
        Optional<Product> foundProduct = productRepository.findById(productDTO.getProductId());
        Product product = foundProduct.orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);

        log.info(product.toString());
        try {
            product.changePrice(productDTO.getPrice());
            product.changeCategory(productDTO.getCategory());
            product.changeDescription(productDTO.getDescription());
            product.changeProductName(productDTO.getProductName());
            productRepository.save(product);

            return new ProductDTO(product);
        } catch (Exception e) {
            throw ProductException.PRODUCT_NOT_UPDATED.get();
        }
    }

    public void remove(int pno) {
        Optional<Product> foundProduct = productRepository.findById(pno);
        Product product = foundProduct.orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);

        try {
            productRepository.delete(product);
        } catch(Exception e) {
            log.error("--- " + e.getMessage());
            throw ProductException.PRODUCT_NOT_REMOVED.get();
        }
    }
}
