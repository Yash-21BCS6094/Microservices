package com.example.order_service.repository;
import com.example.order_service.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByStatus(String status, Pageable pageable); // Fixed order
    List<Order> findByUserId(UUID userId); // Correct
}
