package com.example.order_service.model.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressDto {
    @NotBlank
    private String street;
    @NotBlank
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}