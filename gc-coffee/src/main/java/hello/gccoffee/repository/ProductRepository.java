package hello.gccoffee.repository;

import hello.gccoffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    //지워야함
    Product findByProductName(String productName);
}
