package com.example.order_service.model.dto;
import java.util.List;
import java.util.UUID;

public class ProductDetails {
    private UUID prodId;
    private String prodName;
    private String quantity;
    private List<OrderDetails> orderDetailsList;

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public UUID getProdId() {
        return prodId;
    }

    public void setProdId(UUID prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
