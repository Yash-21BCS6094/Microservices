package com.example.order_service.model.entity;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_products")
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // Creates a foreign key reference
    private Order order;

    @Column(name = "prod_id", nullable = false)
    private UUID prodId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public UUID getProdId() {
        return prodId;
    }

    public void setProdId(UUID prodId) {
        this.prodId = prodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

