package com.example.order_service.externalService;

import com.example.order_service.config.FeignClientInterceptor;
import com.example.order_service.model.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "USER-SERVICE", configuration = FeignClientInterceptor.class)
public interface UserService {
    @GetMapping("/api/v1/users")
    ResponseEntity<UserDto> getUsers(@RequestParam("userId") UUID userId);
    @GetMapping("/api/v1/users")
    ResponseEntity<UserDto> getUsersByUsername(@RequestParam("email") String email);
}

