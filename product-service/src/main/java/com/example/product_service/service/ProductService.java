package com.example.product_service.service;
import com.example.product_service.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(UUID productId, ProductDto productDto);
    ProductDto getProductById(UUID productId);
    Page<ProductDto> getAllProduct(Pageable pageable);
    Page<ProductDto> getProductSortedByPrice(Pageable pageable);
    List<ProductDto> getProductByPriceRange(Double minPrice, Double maxPrice);
    void deleteProduct(UUID productId);
    List<UUID> getAllIds();
}
