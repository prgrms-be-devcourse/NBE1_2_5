package hello.gccoffee.service;

import hello.gccoffee.dto.ProductDTO;
import hello.gccoffee.entity.Product;
import hello.gccoffee.exception.ProductException;
import hello.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;



    public ProductDTO read(int pno){     //상품 조회
        Product product = productRepository.findById(pno).orElseThrow(ProductException.NOT_FOUND::get);
        return new ProductDTO(product);
    }

}
