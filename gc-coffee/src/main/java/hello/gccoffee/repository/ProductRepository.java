package hello.gccoffee.repository;

import hello.gccoffee.entity.Category;
import hello.gccoffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.productName=:productName")
    Optional<Product> findByProductName(@Param("productName") String productName);

}
