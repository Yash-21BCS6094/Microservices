package com.example.product_service.controller;

import com.example.product_service.exception.ProductNotFoundException;
import com.example.product_service.model.dto.ProductDto;
import com.example.product_service.model.entity.Product;
import com.example.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sorted", required = false) Boolean sorted,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        System.out.println("In get Products using id");
        if (id != null) {
            try {
                UUID productId = UUID.fromString(id);
                return ResponseEntity.ok(productService.getProductById(productId));
            } catch (IllegalArgumentException e) {
                throw new ProductNotFoundException("Cannot find product");
            }
        } else if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(productService.getProductByPriceRange(minPrice, maxPrice));
        } else if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDto> products = (sorted != null && sorted)
                    ? productService.getProductSortedByPrice(pageable)
                    : productService.getAllProduct(pageable);
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.badRequest().body("Invalid request parameters.");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UUID>> getAllProductIds(){
        List<UUID> allIds = productService.getAllIds();
        return new ResponseEntity<>(allIds, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> saveOrUpdateProduct(
            @RequestBody ProductDto productDTO,
            @RequestParam(value = "id", required = false) UUID id) {
        ProductDto product = (id == null)
                ? productService.createProduct(productDTO)
                : productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestParam UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
