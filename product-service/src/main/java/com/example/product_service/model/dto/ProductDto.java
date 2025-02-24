package com.example.product_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ProductDto {
    private UUID prodId;
    @NotBlank
    private String name;
    @NotNull
    private double price;

    public UUID getProdId() {
        return prodId;
    }

    public void setProdId(UUID prodId) {
        this.prodId = prodId;
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
