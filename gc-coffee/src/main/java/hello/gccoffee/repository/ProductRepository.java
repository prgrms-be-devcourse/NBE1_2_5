package hello.gccoffee.repository;

import hello.gccoffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query("SELECT p FROM Product p WHERE p.productName=:productName")
//    Optional<Product> findByProductName(@Param("productName") String productName);

    Product findByProductName(String productName);
}
