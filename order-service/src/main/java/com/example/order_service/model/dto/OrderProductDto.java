package com.example.order_service.model.dto;
import com.example.order_service.exception.ProductNotFoundException;
import com.example.order_service.exception.UserNotFoundException;

import java.util.UUID;

public class OrderProductDto {

    private UUID id;
    private UUID prodId;
    private int quantity;

    public OrderProductDto() {
    }

    public OrderProductDto(UUID id, UUID orderId, int quantity) {
        this.id = id;
        this.prodId = prodId;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {

        this.id = id;
    }


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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
