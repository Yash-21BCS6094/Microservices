package com.example.order_service.model.dto;

import com.example.order_service.exception.ProductNotFoundException;
import com.example.order_service.exception.UserNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ProductDto {
    @NotNull
    private UUID prodId;
    @NotBlank
    private String name;
    @NotNull
    private double price;

    public UUID getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        try{
            this.prodId = UUID.fromString(prodId);
        }catch (Exception ex){
            throw new ProductNotFoundException("Incorrect UUID for product");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
