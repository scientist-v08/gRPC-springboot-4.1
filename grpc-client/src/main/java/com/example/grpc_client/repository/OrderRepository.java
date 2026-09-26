package com.example.grpc_client.repository;

import com.example.grpc_client.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
