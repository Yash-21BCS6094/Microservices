package com.example.product_service.repository;
import com.example.product_service.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
