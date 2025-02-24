package com.example.product_service.service.impl;
import com.example.product_service.exception.ProductNotFoundException;
import com.example.product_service.model.dto.ProductDto;
import com.example.product_service.model.entity.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (productDto.getName() != null && !productDto.getName().isEmpty()) {
            existingProduct.setName(productDto.getName());
        }
        if (productDto.getPrice() > 0) {
            existingProduct.setPrice(productDto.getPrice());
        }
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public Page<ProductDto> getAllProduct(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public Page<ProductDto> getProductSortedByPrice(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("price").ascending()
        );

        Page<Product> products = productRepository.findAll(sortedPageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public List<ProductDto> getProductByPriceRange(Double minPrice, Double maxPrice) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Cannot find product")
        );
        productRepository.deleteById(productId);
    }

    @Override
    public List<UUID> getAllIds() {
        List<Product> products = productRepository.findAll();
        List<UUID> ids = products.stream().map(prod -> prod.getId()).toList();
        return ids;
    }
}
