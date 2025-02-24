package com.example.order_service.externalService;

import com.example.order_service.config.FeignClientInterceptor;
import com.example.order_service.model.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignClientInterceptor.class)
public interface ProductService {
    @GetMapping("/api/v1/products/get-all")
    List<UUID> getAllProductIds();
}

