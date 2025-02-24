package com.example.order_service.model.dto;
import com.example.order_service.exception.UserNotFoundException;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

public class OrderDto {
    @NotBlank
    private UUID userId;

    @NotBlank
    private String status;
    @NotBlank
    private List<OrderProductDto> products;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        try{
            this.userId = UUID.fromString(userId);
        }catch (Exception ex){
            throw new UserNotFoundException("Incorrect UUID for user");
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProductDto> products) {
        this.products = products;
    }
}
